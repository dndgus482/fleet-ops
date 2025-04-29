<script setup lang="ts">
import { onMounted, ref } from 'vue'
import router from '@/router'
import { agentGroupApi } from '@/api/api.ts'
import type { SimpleJobNameRes } from '@/types/job.ts'
import { useRoute } from 'vue-router'


const route = useRoute()
const ip = String(route.params.ip)
const userName = String(route.params.userName)

const jobs = ref<SimpleJobNameRes[]>([])

async function goToJob(jobId: string) {
  await router.push({ name: 'jobDetail', params: { jobId: jobId } })
}

async function fetchLinkedJobs() {
  jobs.value = await agentGroupApi.getAgentLinkedJobs(ip, userName)
}

onMounted(fetchLinkedJobs)
</script>

<template>
  <div class="p-6 max-w-4xl mx-auto bg-white shadow-lg rounded-lg relative">
    <h1 class="text-2xl font-bold mb-4">Linked Job List</h1>
    <div class="w-full mt-4">
      <div class="grid grid-cols-3 text-gray-500 text-sm font-semibold pb-2 border-b text-left">
        <div class="px-2">Job Name</div>
      </div>
      <ul class="divide-y divide-gray-100">
        <li v-for="job in jobs" :key="job.jobId" @click="goToJob(job.jobId)"
            class="bg-gray-50 rounded-lg p-4 mb-3 shadow hover:shadow-md transition cursor-pointer">
          <div class="grid grid-cols-3 items-center text-left border-b border-gray-200 pb-3 mb-3">
            <div class="px-2 font-medium text-gray-800">{{ job.jobName }}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
</style>
