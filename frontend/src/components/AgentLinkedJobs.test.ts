import { flushPromises, mount } from '@vue/test-utils'
import AgentLinkedJobs from '@/components/AgentLinkedJobs.vue'
import { agentGroupApi } from '@/api/api.ts'

// ✅ constants
const TESTID = {
  list: '[data-testid="job-list"]',
  job: (id: string) => `[data-testid="job-${id}"]`,
}

const MOCK_JOB_ID = {
  first: 'job-1',
  second: 'job-2',
}

const PROPS = {
  ip: '127.0.0.1',
  userName: MOCK_JOB_ID.first,
}

const MOCK_JOBS = [
  { jobId: MOCK_JOB_ID.first, jobName: 'Job A' },
  { jobId: MOCK_JOB_ID.second, jobName: 'Job B' },
]

const EXPECTED_PUSH_PARAMS = {
  name: 'jobDetail',
  params: { jobId: MOCK_JOB_ID.first },
}

// ✅ mocks
vi.spyOn(agentGroupApi, 'getAgentLinkedJobs').mockResolvedValue({
  data: MOCK_JOBS,
} as any)

const pushMock = vi.fn()
vi.mock('vue-router', () => ({
  useRouter: () => ({
    push: pushMock,
  }),
}))


// ✅ test cases
describe('AgentLinkedJobs.vue', () => {
  it('renders job list from API', async () => {
    const wrapper = mount(AgentLinkedJobs, {props: PROPS})
    await flushPromises()

    const list = wrapper.get(TESTID.list)
    expect(list.findAll('[data-testid^="job-"]')).toHaveLength(2)
    expect(wrapper.text()).toContain(MOCK_JOBS[0].jobName)
    expect(wrapper.text()).toContain(MOCK_JOBS[1].jobName)
  })

  it('navigates to job detail when clicked', async () => {
    const wrapper = mount(AgentLinkedJobs, {props: PROPS})
    await flushPromises()

    const item = wrapper.get(TESTID.job(MOCK_JOB_ID.first))
    await item.trigger('click')

    expect(pushMock).toHaveBeenCalledWith(EXPECTED_PUSH_PARAMS)
  })
})
