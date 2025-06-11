<script setup lang="ts">
import { NButton } from 'naive-ui'
import { Icon } from '@iconify/vue'

const props = defineProps<{
  icon: string
}>()
</script>

<template>
  <NButton v-bind="$attrs">
    <template #icon>
      <n-icon>
        <Icon :icon="props.icon" />
      </n-icon>
    </template>
    <slot />
  </NButton>
</template>
