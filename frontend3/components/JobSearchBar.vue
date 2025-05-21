<script setup lang="ts">
  import { ref } from 'vue'
  import type { SearchJobFilter } from '@/types/job.ts'

  const props = defineProps<{
    searchKeyword: SearchJobFilter
  }>()

  const emit = defineEmits<{
    (e: 'update:searchKeyword', val: SearchJobFilter): void
  }>()

  const selectedType = ref<keyof SearchJobFilter>('jobName')
  const searchInput = ref('')

  const filterKeys = Object.keys(props.searchKeyword) as (keyof SearchJobFilter)[]

  const updateKeyword = () => {
    const next: SearchJobFilter = {
      ...props.searchKeyword,
      [selectedType.value]: searchInput.value,
    }
    emit('update:searchKeyword', next)
  }

</script>

<template>
  <div class="flex items-center gap-2 mb-4">
    <select v-model="selectedType" class="border rounded px-2 py-1 text-sm">
      <option v-for="key in filterKeys" :key="key" :value="key">
        {{ key }}
      </option>
    </select>
    <input
      v-model="searchInput"
      @input="updateKeyword"
      class="border rounded px-3 py-1 w-full text-sm"
      placeholder="Type name"
    />
  </div>
</template>
