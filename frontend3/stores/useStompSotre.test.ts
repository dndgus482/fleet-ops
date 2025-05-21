/// <reference types="vitest/globals" />
import { setActivePinia, createPinia } from 'pinia'
import { useStompStore } from './useStompSotre.ts'

// mock stompClient
vi.mock('@/api/ws/wsClient.ts', () => {
  return {
    stompClient: {
      subscribe: vi.fn(),
      unsubscribe: vi.fn(),
      disconnect: vi.fn(),
      isConnected: vi.fn(() => true),
    },
  }
})

import { stompClient } from '@/api/ws/wsClient.ts'
import { wsTopics } from '@/api/api.ts'

describe('useStompStore', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    vi.clearAllMocks()
  })

  it('should subscribe and store callback', () => {
    const JOB_ID = 'job1'
    const JOB_EXECUTION_NO = 1
    const TOPIC = wsTopics.jobExecutionChanges(JOB_ID, JOB_EXECUTION_NO)

    const store = useStompStore()
    const cb = vi.fn()
    const ref = store.subscribeJobExecutionsChanges(JOB_ID, JOB_EXECUTION_NO, cb)

    expect(stompClient.subscribe).toHaveBeenCalledWith(
      TOPIC,
      expect.any(Function)
    )

    const snapshot = store.getMapSnapshot
    expect(snapshot[TOPIC]).toBeTruthy()
    expect(typeof ref.unsubscribe).toBe('function')
  })

  it('should unsubscribe when no more handlers remain', () => {
    const JOB_ID = 'job1'
    const JOB_EXECUTION_NO = 1
    const TOPIC = wsTopics.jobExecutionChanges(JOB_ID, JOB_EXECUTION_NO)

    const store = useStompStore()
    const cb = vi.fn()
    const ref = store.subscribeJobExecutionsChanges(JOB_ID, JOB_EXECUTION_NO, cb)

    ref.unsubscribe()

    expect(stompClient.unsubscribe).toHaveBeenCalledWith(TOPIC)
  })

  it('should not unsubscribe if other handlers exist', () => {
    const JOB_ID = 'job1'
    const JOB_EXECUTION_NO = 1
    const TOPIC = wsTopics.jobExecutionChanges(JOB_ID, JOB_EXECUTION_NO)

    const store = useStompStore()
    const cb1 = vi.fn()
    const cb2 = vi.fn()

    const ref1 = store.subscribeJobExecutionsChanges(JOB_ID, JOB_EXECUTION_NO, cb1)
    const ref2 = store.subscribeJobExecutionsChanges(JOB_ID, JOB_EXECUTION_NO, cb2)

    ref1.unsubscribe()
    expect(stompClient.unsubscribe).not.toHaveBeenCalled()

    ref2.unsubscribe()
    expect(stompClient.unsubscribe).toHaveBeenCalledWith(TOPIC)
  })

  it('disconnects the client', () => {
    const store = useStompStore()
    store.disconnect()
    expect(stompClient.disconnect).toHaveBeenCalled()
  })

  it('checks connection state', () => {
    const store = useStompStore()
    expect(store.isConnected()).toBe(true)
  })
})
