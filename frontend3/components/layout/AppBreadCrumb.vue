<script setup lang="ts">
  import type { BreadcrumbItem } from '#ui/components/Breadcrumb.vue'

  type BreadcrumbResolver = (route: any) => {
    path: string
    label: string
    parent?: string
  }

  const breadcrumbList: BreadcrumbResolver[] = [
    (route: any) => ({ path: '/agentGroups', label: 'Agent Groups' }),
    (route: any) => ({
      path: `/agentGroups/${route.params.agentGroupId}`,
      label: `${route.params.agentGroupId}`,
      parent: '/agentGroups',
    }),
    (route: any) => ({ path: '/jobs', label: 'Jobs' }),
    (route: any) => ({ path: `/jobs/${route.params.jobId}`, label: `${route.params.jobId}`, parent: '/jobs' }),
  ]

  function useBreadcrumbs() {
    const route = useRoute()

    function buildBreadcrumbs(path: string): BreadcrumbItem[] {
      const matched = breadcrumbList.find(it => it(route).path === path)?.(route)
      if (!matched) return []

      const parentCrumbs = matched.parent ? buildBreadcrumbs(matched.parent) : []
      return [...parentCrumbs, { label: matched.label, to: matched.path }]
    }

    return computed(() => buildBreadcrumbs(route.path))
  }

  const breadcrumb = useBreadcrumbs()


</script>

<template>
  <UBreadcrumb separator-icon="mdi:slash-forward" :items="breadcrumb" />
</template>
