<script setup lang="ts">
  defineProps<{
    data: Record<string, string | number>
  }>()
</script>

<template>
  <div class="border border-neutral-200 rounded text-sm">
    <table class="w-full">
      <tbody>
      <tr v-for="(value, key) in data" :key="key" class="flex">
        <th class="text-left bg-neutral-100 border-r border-neutral-200 px-4 py-2 font-medium flex-1">
          {{ key }}
        </th>
        <td class="px-4 py-2 flex-2">
          {{ value }}
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>