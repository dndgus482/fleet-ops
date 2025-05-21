<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
  import { useDataMode } from '~/composables/useDataMode'

  const route = useRoute()
  const agentGroupId = route.params.agentGroupId as string
  const { mode, isExistingMode } = useDataMode(() => agentGroupId === 'new')

</script>

<template>
  <AgentGroupDetail :agentGroupId="agentGroupId" :dataMode="mode" />
  <br><br><br><br>
  <AgentGroupLinkedJobs v-if="isExistingMode" />
</template>
