<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { useBreadcrumb } from '@/stores/useBreadCrumb.ts'

  const router = useRouter()
  const breadCrumb = useBreadcrumb()
</script>

<template>
  <n-breadcrumb>
    <n-breadcrumb-item @click="() => router.push(item.to)" v-for="item in breadCrumb">
      {{ item.label }}
    </n-breadcrumb-item>
  </n-breadcrumb>
</template>
