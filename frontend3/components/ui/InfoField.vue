<script setup lang="ts">
  /**
   * InfoField.vue
   *
   * A simple label + value display component.
   *
   * Usage:
   * <InfoField label="Job Name" :value="job.name" />
   */
  defineProps({
    label: String,
    value: [String, Number],
  });
</script>

<template>
  <div class="space-y-1">
    <label class="block font-medium">{{ label }}</label>
    <p class="text-gray-700">{{ value }}</p>
  </div>
</template>

<style scoped>
</style>
