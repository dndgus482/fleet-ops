import { flushPromises, mount } from '@vue/test-utils'
import AgentDetail from '@/components/AgentDetail.vue'

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

// ✅ tests
describe('AgentDetail', () => {
  it('renders agent info', () => {
    const wrapper = mount(AgentDetail, {
      props: {
        ip: '127.0.0.1',
        userName: 'admin',
      },
    })
    expect(wrapper.get(TESTID.ip).text()).toContain(MOCK_IP)
    expect(wrapper.get(TESTID.userName).text()).toContain(MOCK_USER)
  })

  it('calls API and shows status + log', async () => {
    const wrapper = mount(AgentDetail, {
      props: {
        ip: '127.0.0.1',
        userName: 'admin',
      },
    })

    await wrapper.get(TESTID.btn).trigger('click')
    await flushPromises()

    expect(wrapper.get(TESTID.status).text()).toBe('Failed')
    expect(wrapper.get(TESTID.errorLog).text()).toContain(MOCK_LOG)
  })
})
