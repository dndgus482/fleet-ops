<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useFormMode } from '@/composables/useFormMode'
import { type AgentGroup, defaultAgentGroup } from '@/types/agentGroup.ts'
import { getHashColor } from '@/util/hashColors.ts'
import { ref, watch } from 'vue'
import { NButton, NTag, useDialog } from 'naive-ui'
import { agentGroupApi } from '@/api/api.ts'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import {
  agentColumn,
  testAllConnections,
} from '@/components/AgentGroupDetailSchema.ts'
import { DataMode } from '@/composables/useDataMode'
import AgentGroupDetailEdit from '@/components/AgentGroupDetailEdit.vue'
import { useAsyncFn } from '@/composables/useAsyncFn'

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

const fetchedAgentGroup = ref<AgentGroup>(defaultAgentGroup())
const { isReady, execute: fetchAgentGroup } = useAsyncFn(async () => {
  fetchedAgentGroup.value = (
    await agentGroupApi.getAgentGroupById(agentGroupId)
  ).data
})

const {
  isLoading: testAllConnectionsLoading,
  execute: executeTestAllConnections,
} = useAsyncFn(async () => {
  await testAllConnections(fetchedAgentGroup.value.agents)
})

watch(isReady, () => {
  if (isReady.value) {
    executeTestAllConnections()
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
    await fetchAgentGroup()
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
  await router.push({ name: 'agentGroupList' })
}

if (!isCreateMode.value) {
  fetchAgentGroup()
}
</script>

<template>
  <n-card v-if="isReadMode" :title="fetchedAgentGroup.agentGroupName ?? ''">
    <template #header-extra>
      <n-button type="default" @click="pressUpdate" data-testid="updateButton">
        Edit
      </n-button>
      <n-button type="error" @click="pressDelete" data-testid="deleteButton">
        Delete
      </n-button>
    </template>
    <n-form v-if="isReady">
      <n-form-item label="Group Description">
        <n-input
          readonly
          type="textarea"
          v-model:value="fetchedAgentGroup.agentGroupDescription"
          data-testid="agentGroupDescriptionInput"
        />
      </n-form-item>
      <n-form-item label="Tags">
        <n-tag
          v-for="(tag, index) in fetchedAgentGroup.tags"
          :key="tag + index"
          :bordered="false"
          :style="{ backgroundColor: getHashColor(tag) }"
          type="default"
        >
          {{ tag }}
        </n-tag>
      </n-form-item>
      <n-form-item label="Agents">
        <n-space vertical class="w-full">
          <div class="flex justify-end">
            <n-button
              size="small"
              @click="executeTestAllConnections()"
              :loading="testAllConnectionsLoading"
            >
              Connection Test
            </n-button>
          </div>
          <n-data-table
            :columns="[
              agentColumn.logColumn(),
              agentColumn.ipColumn(),
              agentColumn.userNameColumn(),
              agentColumn.statusColumn(),
            ]"
            :data="fetchedAgentGroup.agents"
          />
        </n-space>
      </n-form-item>
    </n-form>
    <n-form v-else>
      <n-skeleton text :repeat="1" style="width: 200px; height: 32px" />
      <n-form-item label="Group Description">
        <n-skeleton text :repeat="4" />
      </n-form-item>
      <n-form-item label="Tags">
        <n-space wrap>
          <n-skeleton v-for="i in 3" :key="i" :width="60" :height="24" />
        </n-space>
      </n-form-item>
      <n-form-item label="Agents">
        <n-skeleton text :repeat="6" />
      </n-form-item>
    </n-form>
  </n-card>
  <AgentGroupDetailEdit
    v-else
    :agent-group="fetchedAgentGroup"
    :form-mode="formMode"
    @cancel="cancel"
    @save="save"
  />
</template>
