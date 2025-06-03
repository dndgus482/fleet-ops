<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useFormMode } from '@/composables/useFormMode'
import { type AgentGroup, defaultAgentGroup } from '@/types/agentGroup.ts'
import { ref, watchEffect } from 'vue'
import { useDialog } from 'naive-ui'
import { agentGroupApi } from '@/api/api.ts'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import {
  agentColumn,
  testAllConnections,
} from '@/components/AgentGroupDetailSchema.ts'
import { DataMode } from '@/composables/useDataMode'
import AgentGroupDetailEdit from '@/components/AgentGroupDetailEdit.vue'
import { useAsyncFn } from '@/composables/useAsyncFn'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { getHashColor } from '@/util/hashColors.ts'
import { useAsyncState } from '@vueuse/core'

const { agentGroupId, dataMode } = defineProps<{
  agentGroupId: string
  dataMode: DataMode
}>()

const router = useRouter()
const dialog = useDialog()

const updateFlag = ref(false)
const { formMode, isCreateMode, isReadMode } = useFormMode({
  dataMode,
  updateFlag,
})

const fetchAgentGroup = useAsyncState<AgentGroup>(
  async () => (await agentGroupApi.getAgentGroupById(agentGroupId)).data,
  defaultAgentGroup(),
  { immediate: false },
)

const connectionTest = useAsyncFn(async () => {
  await testAllConnections(fetchAgentGroup.state.value.agents)
})

watchEffect(() => {
  if (fetchAgentGroup.isReady.value) {
    connectionTest.execute()
  }
})

function pressUpdate() {
  updateFlag.value = true
}

function cancel() {
  if (isCreateMode.value) {
    router.back()
  } else {
    updateFlag.value = false
  }
}

async function save(agentGroupId: string) {
  if (isCreateMode.value) {
    await router.replace({
      name: 'agentGroupDetail',
      params: { agentGroupId },
    })
  } else {
    updateFlag.value = false
    await fetchAgentGroup.execute()
  }
}

async function pressDelete() {
  dialog.create({
    ...dialogOptions,
    type: 'error',
    title: 'Confirm Delete',
    content: 'Are you sure you want to delete?',
    negativeText: 'Cancel',
    positiveText: 'Delete',
    onPositiveClick: deleteAgentGroup,
  })
}

async function deleteAgentGroup() {
  await agentGroupApi.deleteAgentGroupById(agentGroupId)
  await router.replace({ name: 'agentGroupList' })
}

if (!isCreateMode.value) {
  fetchAgentGroup.execute()
}
</script>

<template>
  <n-card
    v-if="isReadMode"
    :title="fetchAgentGroup.state.value.agentGroupName ?? ''"
    :segmented="{
      content: true,
    }"
  >
    <template #header-extra>
      <div class="flex gap-2">
        <base-icon-button
          type="default"
          secondary
          @click="pressUpdate"
          data-testid="updateButton"
          icon="lucide:pencil"
        >
          Edit
        </base-icon-button>
        <base-icon-button
          type="error"
          secondary
          @click="pressDelete"
          data-testid="deleteButton"
          icon="lucide:trash-2"
        >
          Delete
        </base-icon-button>
      </div>
    </template>

    <!-- Group Description -->
    <n-thing v-if="fetchAgentGroup.isReady.value">
      <template #header>Group Description</template>
      <div class="whitespace-pre-wrap">
        {{ fetchAgentGroup.state.value.agentGroupDescription }}
      </div>
    </n-thing>
    <n-skeleton v-else text />
    <n-divider />

    <!-- Tags -->
    <n-thing v-if="fetchAgentGroup.isReady.value">
      <template #header>Tags</template>
      <div class="flex gap-2">
        <n-tag
          v-for="(tag, index) in fetchAgentGroup.state.value.tags"
          :key="tag + index"
          :bordered="false"
          :color="{ color: getHashColor(tag) }"
          type="default"
        >
          {{ tag }}
        </n-tag>
      </div>
    </n-thing>
    <n-skeleton v-else />
    <n-divider />

    <!-- Agents -->
    <n-thing v-if="fetchAgentGroup.isReady.value">
      <template #header>Agents</template>
      <template #header-extra>
        <base-icon-button
          size="small"
          @click="connectionTest.execute()"
          :loading="connectionTest.isLoading.value"
          icon="lucide:activity"
        >
          Connection Test
        </base-icon-button>
      </template>
      <n-space vertical class="w-full">
        <n-data-table
          :columns="[
            agentColumn.logColumn(),
            agentColumn.ipColumn(),
            agentColumn.userNameColumn(),
            agentColumn.statusColumn(),
          ]"
          :data="fetchAgentGroup.state.value.agents"
        />
      </n-space>
    </n-thing>
    <n-skeleton v-else />
  </n-card>
  <agent-group-detail-edit
    v-else
    :agent-group="fetchAgentGroup.state.value"
    :form-mode="formMode"
    @cancel="cancel"
    @save="save"
  />
</template>
