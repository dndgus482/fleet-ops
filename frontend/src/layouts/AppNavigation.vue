<script setup lang="ts">
  import { computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import type { MenuOption } from 'naive-ui'

  const menuOptions: MenuOption[] = [
    {
      label: 'Jobs',
      key: '/jobs',
    },
    {
      label: 'Agent Groups',
      key: '/agentGroups',
    },
  ]

  const router = useRouter()
  const route = useRoute()
  const activeKey = computed(() => {
    // active if starts with menu path
    const path = route.path
    const match = menuOptions.find(opt =>
      typeof opt.key === 'string' && path.startsWith(opt.key),
    )
    return match?.key ?? null
  })

  const handleUpdate = (key: string) => {
    router.push(key)
  }
</script>

<template>
  <n-menu
    :options="menuOptions"
    :value="activeKey"
    @update:value="handleUpdate"
  />
</template>

<style scoped>

</style>