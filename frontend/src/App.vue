<script setup lang="ts">
import AppLayout from '@/layouts/AppLayout.vue'
import hljs from 'highlight.js/lib/core'
import bash from 'highlight.js/lib/languages/bash'

hljs.registerLanguage('bash', bash)

const themeOverrides = {
  common: {
    primaryColor: '#38bdf8', // sky-400
  },
}
</script>

<template>
  <n-config-provider :hljs="hljs" :theme-overrides="themeOverrides">
    <n-dialog-provider>
      <app-layout>
        <router-view />
      </app-layout>
    </n-dialog-provider>
  </n-config-provider>
</template>
