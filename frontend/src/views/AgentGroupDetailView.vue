<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
  import AgentGroupDetail from '@/components/AgentGroupDetail.vue'
  import AgentGroupLinkedJobs from '@/components/AgentGroupLinkedJobs.vue'
  import { useDataMode } from '@/composables/useDataMode'

  const { agentGroupId } = defineProps<{
    agentGroupId: string
  }>()
  const { mode, isExistingMode } = useDataMode(() => agentGroupId === 'new')

</script>

<template>
  <AgentGroupDetail :agentGroupId="agentGroupId" :dataMode="mode" />
  <br><br><br><br>
  <AgentGroupLinkedJobs v-if="isExistingMode" />
</template>
