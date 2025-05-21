<script setup lang="ts">
  import type { NavigationMenuItem } from '#ui/components/NavigationMenu.vue'

  const route = useRoute()
  const items = computed<NavigationMenuItem[][]>(() => ([
    [
      {
        label: 'Agents',
        icon: 'streamline:computer-controller-1-remote-quadcopter-drones-flying-drone-control-controller-technology-fly',
        to: '/agentGroups',
        active: route.path.startsWith('/agentGroups'),
      },
      {
        label: 'Jobs',
        icon: 'carbon:task',
        to: '/jobs',
        active: route.path.startsWith('/jobs'),
      },
    ],
  ]))

</script>

<template>
  <UNavigationMenu orientation="vertical" :items="items" class="data-[orientation=vertical]:w-48" />
</template>

<style scoped>

</style>