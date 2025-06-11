<script setup lang="ts">
import { defineProps } from 'vue'

const { message } = defineProps<{
  message: string | undefined
}>()
</script>

<template>
  <div class="flex items-center gap-1 text-sm text-[color:#D03050]">
    <span>{{ message }}</span>
  </div>
</template>
