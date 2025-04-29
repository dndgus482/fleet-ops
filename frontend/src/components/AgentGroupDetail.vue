<script setup lang="ts">
import { useRoute } from 'vue-router'
import { ref, useTemplateRef } from 'vue'
import router from '@/router'
import { useMode } from '../composables/useMode'
import ConfirmDialog from '@/components/ui/ConfirmDialog.vue'

import { defaultAgentGroup, type AgentGroup } from '@/types/agentGroup.ts'
import { agentGroupApi } from '@/api/api.ts'
import { getHashColor } from '@/util/hashColors.ts'

const route = useRoute()
const agentGroupId = String(route.params.agentGroupId)

const isUpdate = ref(false)
const { isCreateMode, isUpdateMode, isReadMode } = useMode({
  createCondition: () => agentGroupId === 'new',
  updateCondition: () => isUpdate.value
})

const saveDialog = useTemplateRef('saveDialog')
const deleteDialog = useTemplateRef('deleteDialog')
const agentGroupConnectionDialog = useTemplateRef('agentGroupConnectionDialog')

const currentAgentGroup = ref<AgentGroup>(defaultAgentGroup())
const fetchedAgentGroup = ref<AgentGroup | null>(null)

function pressUpdate() {
  isUpdate.value = true
}

function pressCancel() {
  if (isCreateMode.value) {
    router.back()
  } else {
    isUpdate.value = false
  }
}

async function pressSave() {
  if (!saveDialog.value) {
    return
  }
  const result = await saveDialog.value.reveal()
  if (result.isCanceled) {
    return
  }
  await saveAgentGroup()
}

async function pressDelete() {
  if (!deleteDialog.value) {
    return
  }
  const result = await deleteDialog.value.reveal()
  if (result.isCanceled) {
    return
  }
  await deleteAgentGroup()
}

async function fetchAgentGroup() {
  const agentGroup = (await agentGroupApi.getAgentGroupById(agentGroupId)).data
  fetchedAgentGroup.value = agentGroup
  currentAgentGroup.value = agentGroup
}

async function saveAgentGroup() {
  if (isUpdateMode.value) {
    await agentGroupApi.updateAgentGroup(currentAgentGroup.value.agentGroupId, currentAgentGroup.value)
  } else {
    await agentGroupApi.createAgentGroup(currentAgentGroup.value)
  }
  await router.push({ name: 'agentGroupList' })
}

async function deleteAgentGroup() {
  await agentGroupApi.deleteAgentGroupById(currentAgentGroup.value.agentGroupId)
  await router.push({ name: 'agentGroupList' })
}


const newTag = ref('')
const newAgentUser = ref('')
const newAgentIp = ref('')

function addTag() {
  if (newTag.value.trim()) {
    currentAgentGroup.value.tags.push(newTag.value.trim())
    newTag.value = ''
  }
}

function addAgent() {
  if (newAgentUser.value.trim() && newAgentIp.value.trim()) {
    currentAgentGroup.value.agents.push({ userName: newAgentUser.value.trim(), ip: newAgentIp.value.trim() })
    newAgentUser.value = ''
    newAgentIp.value = ''
  }
}

function removeTag(index: number) {
  currentAgentGroup.value.tags.splice(index, 1)
}

function removeAgent(index: number) {
  currentAgentGroup.value.agents.splice(index, 1)
}

async function testAllConnections() {
  // TODO: GroupId 가 아니라 현재 노드 리스트 받아야함.
  const agentConnectionRes = (await agentGroupApi.agentGroupConnectionTest(currentAgentGroup.value.agentGroupId)).data
  for (const agent of agentConnectionRes) {
    if (agent.connected) {
      if (!agentGroupConnectionDialog.value) {
        return
      }
      await agentGroupConnectionDialog.value.reveal()
    }
  }
}

if (isReadMode.value) {
  fetchAgentGroup()
}

</script>

<template>
  <div class="p-6 max-w-xl mx-auto bg-white shadow-lg rounded-xl">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-xl font-semibold">Group Editor</h2>
      <div class="space-x-2">
        <button v-if="isReadMode" @click="pressUpdate" class="btn-primary">Edit</button>
        <button v-if="isReadMode" @click="pressDelete" class="btn-danger">Delete</button>
      </div>
    </div>

    <div class="space-y-3">
      <label class="block">Group Name</label>
      <input :disabled="isReadMode" v-model="currentAgentGroup.agentGroupName" class="input-field" />

      <label class="block">Group Description</label>
      <textarea :disabled="isReadMode" v-model="currentAgentGroup.agentGroupDescription"
                class="input-field h-24"></textarea>
    </div>

    <div class="mb-6">
      <label class="block mb-1 font-medium">Tags</label>
      <div class="flex gap-2 mb-3">
        <input v-model="newTag" class="border px-2 py-1 rounded w-full" placeholder="Add tag" />
        <button @click="addTag" class="bg-indigo-500 text-white px-3 py-1 rounded">Add</button>
      </div>
      <div class="flex flex-wrap gap-2">
        <span
          v-for="(tag, index) in currentAgentGroup.tags"
          :key="tag + index"
          :class="['text-xs font-semibold text-gray-800 px-2 py-1 rounded', getHashColor(tag)]"
        >
          {{ tag }}
          <button @click.stop="removeTag(index)" class="text-xs text-red-500 hover:text-red-700">×</button>
        </span>
      </div>
    </div>

    <div class="mb-6">
      <div class="flex items-center justify-between mb-1">
        <label class="font-medium text-sm">Agents</label>
        <button
          @click="testAllConnections"
          class="text-xs px-2 py-0.5 rounded border border-gray-300 text-gray-600 hover:bg-gray-100"
        >
          연결 테스트
        </button>
      </div>
      <div class="flex gap-2 mb-3">
        <input v-model="newAgentUser" class="border px-2 py-1 rounded w-1/2" placeholder="User" />
        <input v-model="newAgentIp" class="border px-2 py-1 rounded w-1/2" placeholder="IP" />
        <button @click="addAgent" class="bg-gray-600 text-white px-3 py-1 rounded">Add</button>
      </div>
      <div class="flex flex-wrap gap-2">
        <span
          v-for="(agent, idx) in currentAgentGroup.agents"
          :key="agent.userName + agent.ip + idx"
          class="bg-gray-100 text-gray-800 px-2 py-1 rounded text-sm flex items-center gap-1"
        >
          {{ agent.userName }}@{{ agent.ip }}
          <button @click.stop="removeAgent(idx)" class="text-xs text-red-500 hover:text-red-700">×</button>
        </span>
      </div>
    </div>

    <div class="flex justify-end mt-4 space-x-2">
      <button v-if="!isReadMode" @click="pressCancel" class="btn-secondary">Cancel</button>
      <button v-if="!isReadMode" @click="pressSave" class="btn-primary">Save</button>
    </div>

    <ConfirmDialog ref="saveDialog" message="Save?" />
    <ConfirmDialog ref="deleteDialog" message="Are you sure to Delete?" />
    <ConfirmDialog ref="agentGroupConnectionDialog" message="Some agents not connected" />
  </div>
</template>

<style scoped>
.btn-primary {
  @apply bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700;
}

.btn-secondary {
  @apply bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500;
}

.btn-danger {
  @apply bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700;
}

.input-field {
  @apply w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500;
}
</style>
