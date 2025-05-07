import { createRouter, createWebHistory } from 'vue-router'
import JobListView from "@/views/JobListView.vue";
import JobDetailView from '@/views/JobDetailView.vue'
import JobExecutionDetailView from '@/views/JobExecutionDetailView.vue'
import JobExecutionListView from '@/views/JobExecutionListView.vue'
import JobHistoryListView from '@/views/JobHistoryListView.vue'
import JobHistoryDetailView from '@/views/JobHistoryDetailView.vue'
import AgentAgentGroupListView from '@/views/AgentGroupListView.vue'
import AgentGroupDetailView from '@/views/AgentGroupDetailView.vue'
import AgentDetailView from '@/views/AgentDetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/jobs',
      name: 'jobList',
      component: JobListView,
    },
    {
      path: '/jobs/:jobId',
      name: 'jobDetail',
      component: JobDetailView,
      props: true
    },
    {
      path: '/jobExecutions',
      name: 'jobExecutionList',
      component: JobExecutionListView,
      props: true
    },
    {
      path: '/jobs/:jobId/executions/:jobExecutionNo',
      name: 'jobExecutionDetail',
      component: JobExecutionDetailView,
      props: true
    },
    {
      path: '/jobs/:jobId/history',
      name: 'jobHistoryList',
      component: JobHistoryListView,
      props: true
    },
    {
      path: '/jobs/:jobId/history/:jobHistoryNo',
      name: 'jobHistoryDetail',
      component: JobHistoryDetailView,
      props: true
    },
    {
      path: '/agentGroups/',
      name: 'agentGroupList',
      component: AgentAgentGroupListView,
      props: true
    },
    {
      path: '/agentGroups/:agentGroupId',
      name: 'agentGroupDetail',
      component: AgentGroupDetailView,
      props: true
    },
    {
      path: '/agents/:ip@:userName',
      name: 'agentDetail',
      component: AgentDetailView,
      props: true
    },
  ],
})

export default router
