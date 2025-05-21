<script lang="ts">
  import { agentGroupApi } from '~/api/api'
  import { useAsyncState } from '@vueuse/core'
  import type { Agent } from '~/types/agentGroup'

  export type StatusType = {
    connected?: boolean
    log?: string
  }

  export function useAgentConnection(agent: MaybeRef<Agent>) {
    const agentRef = toRef(agent)

    async function test(): Promise<StatusType> {
      try {
        const res = await agentGroupApi.agentConnectionTest([agentRef.value])
        return res.data[0]
      } catch (error) {
        return { connected: false, log: 'Could not connect' }
      }
    }

    const { execute, isLoading, state } = useAsyncState<StatusType>(test, {}, { immediate: false })
    const isLogVisible = ref(false)
    watchEffect(() => {
      // visible whenever update state
      if (state.value.log) {
        isLogVisible.value = true
      }
    })
    const statusText = computed(() => {
      return state.value.connected === true ? 'Success' :
        state.value.connected === false ? 'Failed' :
          ''
    })

    return { execute, isLoading, state, isLogVisible, statusText }
  }

</script>
