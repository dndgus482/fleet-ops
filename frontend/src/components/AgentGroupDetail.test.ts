import { defineComponent, ref } from 'vue'
import { agentGroupApi } from '@/api/api.ts'
import { mount } from '@vue/test-utils'
import AgentGroupDetail from '@/components/AgentGroupDetail.vue'
import { DataMode } from '@/composables/useDataMode'
import { useAgentGroupForm, useTagField } from '@/components/AgentGroupDetailSchema'

// Use vi.importActual() to partially mock 'naive-ui': this preserves all original exports (such as NButton)
// while replacing useDialog with a test mock. This ensures that UI components remain available for rendering,
// and only useDialog is overridden for testing dialog interactions.
const dialogMock = { create: vi.fn() }
vi.mock('naive-ui', async () => {
  //
  const actual = await vi.importActual<any>('naive-ui')
  return {
    ...actual,
    useDialog: () => dialogMock,
  }
})

beforeEach(() => {
  vi.clearAllMocks()
})

function withSetup(fn: () => void) {
  let error: any
  mount(defineComponent({
    setup() {
      try {
        fn()
      } catch (e) {
        error = e
      }
    },
    mounted() {
      if (error) throw error
    },
    template: `
      <div />`,
  }))
}

describe('useTagField', () => {
  it('adds and removes tags correctly', () => {
    withSetup(() => {
      useAgentGroupForm()
      const { input, tags, add, remove } = useTagField()
      input.value = 'hello'
      add()
      expect(tags.value).toContain('hello')
      remove(0)
      expect(tags.value).toHaveLength(0)
    })
  })

  it('error feedback when add empty input', () => {
    withSetup(() => {
      useAgentGroupForm()
      const { tags, errorMessage, add } = useTagField()

      add()
      expect(tags.value).toHaveLength(0)
      expect(errorMessage.value?.length).greaterThan(0)
    })
  })

  it('shows dialog if more than 10 tags', () => {
    const tags = Array.from({ length: 10 }, (_, i) => `tag${i}`)
    const mockTags = ref({ tags })
    const { input, add } = useTagField()

    input.value = 'overflow'
    add()
    expect(mockTags.value.tags).toHaveLength(10)
    expect(mockTags.value.tags).not.toContain('overflow')
    expect(dialogMock.create).toHaveBeenCalledWith(expect.objectContaining({
      type: 'error',
      content: 'Maximum 100 tags allowed',
    }))
  })
})

describe('useAgentField', () => {
  it('adds and removes agents correctly', () => {
  })

  it('ignores empty input', () => {
  })

  it('clear input after add')

  it('do connection test properly with registered agents')
})


vi.spyOn(agentGroupApi, 'agentConnectionTest').mockResolvedValue({
  data: [{ connected: false, log: 'connection failed: timeout' }],
} as any)
vi.spyOn(agentGroupApi, 'getAgentGroupById').mockResolvedValue({
  data: {
    agentGroupId: 'test-id',
    agentGroupName: 'Test Group',
    agentGroupDescription: 'desc',
    tags: ['tag1', 'tag2'],
    agents: [
      { ip: '127.0.0.1', userName: 'admin' },
      { ip: '192.168.0.1', userName: 'user' },
    ],
  },
} as any)
vi.spyOn(agentGroupApi, 'updateAgentGroup').mockResolvedValue({
  data: {},
} as any)
vi.spyOn(agentGroupApi, 'createAgentGroup').mockResolvedValue({
  data: {},
} as any)
vi.spyOn(agentGroupApi, 'deleteAgentGroupById').mockResolvedValue({
  data: null,
  status: 200,
} as any)


const pushMock = vi.fn()
vi.mock('vue-router', () => ({
  useRouter: () => ({
    push: pushMock,
  }),
}))

describe('AgentGroupDetail', () => {
  it('form check', async () => {
    // name required, less than 100
    // description optional, less than 1,000
    // tag optional, less than 10
    // agent required, less than 100
    // only ip expr, userName no '@'
    // connection test doesn't need to be passed

    const wrapper = mount(AgentGroupDetail, {
        props: {
          agentGroupId: 'test-id',
          dataMode: DataMode.EXISTING,
        },
      },
    )

    const nameInput = wrapper.find('[data-testid="agentGroupNameInput"]')
    await nameInput.setValue('')
    expect(wrapper.text()).toContain('Name is required')

    await nameInput.setValue('a'.repeat(101))
    expect(wrapper.text()).toContain('Name must be less than 100 characters')

    const descriptionInput = wrapper.find('[data-testid="agentGroupDescriptionInput"]')
    await descriptionInput.setValue('a'.repeat(1001))
    expect(wrapper.text()).toContain('Description must be less than 1000 characters')
  })

  it('if read mode, all fields are disabled', async () => {
    const wrapper = mount(AgentGroupDetail, {
        props: {
          agentGroupId: 'test-id',
          dataMode: DataMode.EXISTING,
        },
      },
    )

    const inputs = wrapper.findAllComponents({ name: 'NInput' })
    for (const input of inputs) {
      expect(input.props('disabled')).toBe(true)
    }
  })

  it('delete correctly', async () => {
    // delete dialog
    // if no, cancel
    // if yes, delete
    // go to group list page after delete
    const wrapper = mount(AgentGroupDetail, {
        props: {
          agentGroupId: 'test-id',
          dataMode: DataMode.EXISTING,
        },
      },
    )

    await wrapper.find('[data-testid="deleteButton"]').trigger('click')
    const dialogOptions = dialogMock.create.mock.calls[0][0]
    await dialogOptions.onNegativeClick()
    expect(agentGroupApi.deleteAgentGroupById).not.toHaveBeenCalled()

    await wrapper.find('[data-testid="deleteButton"]').trigger('click')
    await dialogMock.create.mock.calls[1][0].onPositiveClick()
    expect(agentGroupApi.deleteAgentGroupById).toHaveBeenCalled()
    expect(pushMock).toHaveBeenCalledWith({ name: 'agentGroupList' })
  })

  it('saveAgentGroup should call API and navigate', async () => {
    // save dialog
    // if no, cancel
    // if yes, save
    // go to group list page after save
    const wrapper = mount(AgentGroupDetail, {
        props: {
          agentGroupId: 'test-id',
          dataMode: DataMode.EXISTING,
        },
      },
    )

    await wrapper.find('[data-testid="updateButton"]').trigger('click')

    await wrapper.find('[data-testid="saveButton"]').trigger('click')
    await dialogMock.create.mock.calls[0][0].onNegativeClick()
    expect(agentGroupApi.updateAgentGroup).not.toHaveBeenCalled()

    await wrapper.find('[data-testid="saveButton"]').trigger('click')
    await dialogMock.create.mock.calls[1][0].onPositiveClick()
    expect(agentGroupApi.updateAgentGroup).toHaveBeenCalled()
    expect(pushMock).toHaveBeenCalledWith({ name: 'agentGroupList' })
  })
})
