<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
import AgentGroupDetail from '@/components/AgentGroupDetail.vue'
import AgentGroupLinkedJobs from '@/components/AgentGroupLinkedJobs.vue'
import { useDataMode } from '@/composables/useDataMode'
import { computed } from 'vue'

const { agentGroupId } = defineProps<{
  agentGroupId: string
}>()
const newCondition = computed(() => agentGroupId === 'new')
const { mode, isExistingMode } = useDataMode(newCondition)
</script>

<template>
  <agent-group-detail :agent-group-id="agentGroupId" :data-mode="mode" />
  <br /><br /><br /><br />
  <agent-group-linked-jobs
    v-if="isExistingMode"
    :agent-group-id="agentGroupId"
  />
</template>
