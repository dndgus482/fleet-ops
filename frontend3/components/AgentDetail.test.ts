import { mount, flushPromises } from '@vue/test-utils'
import AgentDetail from './AgentDetail.vue'

// ✅ constants
const MOCK_IP = '127.0.0.1'
const MOCK_USER = 'admin'
const MOCK_LOG = 'connection failed: timeout'

const TESTID = {
  ip: '[data-testid="agent-ip"]',
  userName: '[data-testid="agent-userName"]',
  btn: '[data-testid="test-connection-btn"]',
  status: '[data-testid="connection-status"]',
  errorLog: '[data-testid="error-log"]',
  toggle: '[data-testid="log-toggle"]',
}

// ✅ mocks
vi.mock('@/api/api.ts', () => ({
  agentGroupApi: {
    agentConnectionTest: vi.fn().mockResolvedValue({
      data: [{ connected: false, log: 'connection failed: timeout' }],
    }),
  },
}))

vi.mock('vue-router', () => ({
  useRoute: () => ({
    params: {
      ip: '127.0.0.1',
      userName: 'admin',
    },
  }),
}))

// ✅ tests
describe('AgentDetail', () => {
  it('renders agent info', () => {
    const wrapper = mount(AgentDetail)
    expect(wrapper.get(TESTID.ip).text()).toContain(MOCK_IP)
    expect(wrapper.get(TESTID.userName).text()).toContain(MOCK_USER)
  })

  it('calls API and shows status + log', async () => {
    const wrapper = mount(AgentDetail)

    await wrapper.get(TESTID.btn).trigger('click')
    await flushPromises()

    expect(wrapper.get(TESTID.status).text()).toBe('Failed')
    expect(wrapper.get(TESTID.errorLog).text()).toContain(MOCK_LOG)
  })

  it('toggles log visibility on click', async () => {
    const wrapper = mount(AgentDetail)

    await wrapper.get(TESTID.btn).trigger('click')
    await flushPromises()

    const toggle = wrapper.get(TESTID.toggle)
    expect(wrapper.get(TESTID.errorLog).isVisible()).toBe(true)

    await toggle.trigger('click')
    await wrapper.vm.$nextTick()

    expect(wrapper.find(TESTID.errorLog).exists()).toBe(false)
  })
})
