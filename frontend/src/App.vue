<script setup lang="ts">
  import AppLayout from '@/layouts/AppLayout.vue'

  const themeOverrides = {
    common: {
      primaryColor: '#38bdf8', // sky-400
    },
  }

</script>

<template>
  <n-config-provider :theme-overrides="themeOverrides">
    <n-dialog-provider>
      <app-layout>
        <router-view />
      </app-layout>
    </n-dialog-provider>
  </n-config-provider>
</template>
