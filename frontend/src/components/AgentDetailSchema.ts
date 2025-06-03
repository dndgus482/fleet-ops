import { useAsyncState } from '@vueuse/core'
import { computed, type MaybeRef, ref, toRef, watchEffect } from 'vue'
import type { Agent } from '@/types/agentGroup.ts'
import { agentGroupApi } from '@/api/api.js'

type ConnectResult = {
  connected?: boolean
  log?: string
}

export function useAgentConnection(agent: MaybeRef<Agent>) {
  const agentRef = toRef(agent)

  async function test(): Promise<ConnectResult> {
    try {
      const res = await agentGroupApi.agentConnectionTest([agentRef.value])
      return res.data[0]
    } catch (error) {
      return { connected: false, log: 'Could not connect' }
    }
  }

  const { execute, isLoading, state } = useAsyncState<ConnectResult>(
    test,
    {},
    { immediate: false },
  )
  const connected = computed(() => state.value.connected)
  const log = computed(() => state.value.log)

  const isLogVisible = ref(false)
  watchEffect(() => {
    // visible whenever update state
    if (state.value.log) {
      isLogVisible.value = true
    }
  })
  return { connected, log, execute, isLoading, isLogVisible }
}
