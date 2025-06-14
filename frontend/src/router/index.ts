import {
  createRouter,
  createWebHistory,
  type RouteLocationNormalizedLoadedGeneric,
} from 'vue-router'
import JobListView from '@/views/JobListView.vue'
import JobDetailView from '@/views/JobDetailView.vue'
import JobExecutionDetailView from '@/views/JobExecutionDetailView.vue'
import JobExecutionListView from '@/views/JobExecutionListView.vue'
import JobHistoryListView from '@/views/JobHistoryListView.vue'
import JobHistoryDetailView from '@/views/JobHistoryDetailView.vue'
import AgentGroupListView from '@/views/AgentGroupListView.vue'
import AgentGroupDetailView from '@/views/AgentGroupDetailView.vue'
import AgentDetailView from '@/views/AgentDetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/agentGroups',
      name: 'agentGroupList',
      component: AgentGroupListView,
      props: true,
      meta: {
        breadcrumb: (_: RouteLocationNormalizedLoadedGeneric) => 'Agent Group',
      },
    },
    {
      path: '/agentGroups/:agentGroupId',
      name: 'agentGroupDetail',
      component: AgentGroupDetailView,
      props: true,
      meta: {
        breadcrumb: (route: RouteLocationNormalizedLoadedGeneric) =>
          route.params.agentGroupId,
        parent: 'agentGroupList',
      },
    },
    {
      path: '/agents/:ip/:userName',
      name: 'agentDetail',
      component: AgentDetailView,
      props: true,
      meta: {
        breadcrumb: (route: RouteLocationNormalizedLoadedGeneric) =>
          `Agent - ${route.params.ip} (${route.params.userName})`,
      },
    },
    {
      path: '/jobs',
      name: 'jobList',
      component: JobListView,
    },
    {
      path: '/jobs/:jobId',
      name: 'jobDetail',
      component: JobDetailView,
      props: true,
    },
    {
      path: '/jobExecutions',
      name: 'jobExecutionList',
      component: JobExecutionListView,
      props: true,
    },
    {
      path: '/jobs/:jobId/executions/:jobExecutionNo',
      name: 'jobExecutionDetail',
      component: JobExecutionDetailView,
      props: true,
    },
    {
      path: '/jobs/:jobId/history',
      name: 'jobHistoryList',
      component: JobHistoryListView,
      props: true,
    },
    {
      path: '/jobs/:jobId/history/:jobHistoryNo',
      name: 'jobHistoryDetail',
      component: JobHistoryDetailView,
      props: true,
    },
  ],
})

export default router
