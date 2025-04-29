<script setup lang="ts">
  import { computed, ref } from 'vue'
  import type { SearchJobExecutionFilter } from '@/types/job.ts'

  const props = defineProps<{
    searchKeyword: SearchJobExecutionFilter
  }>()

  const emit = defineEmits<{
    (e: 'update:searchKeyword', val: SearchJobExecutionFilter): void
  }>()

  const selectedType = ref<keyof SearchJobExecutionFilter>('jobId')
  const searchInput = ref('')

  // 화면에서 사용하는 배열 형식의 키워드
  const filterKeys = Object.keys(props.searchKeyword) as (keyof SearchJobExecutionFilter)[]
  const internalKeywords = computed(() =>
    Object.entries(props.searchKeyword)
      .map(([key, value]) => ({
        type: key,
        keyword: value,
      }))
      .filter(({ keyword }) => keyword !== ''),
  )

  const updateKeyword = (type: keyof SearchJobExecutionFilter, keyword: string) => {
    const next: SearchJobExecutionFilter = {
      ...props.searchKeyword,
      [type]: keyword,
    }
    emit('update:searchKeyword', next)
  }

  const addSearch = () => {
    const keyword = searchInput.value.trim()
    if (!keyword) return

    const type = selectedType.value as keyof SearchJobExecutionFilter
    const exists = internalKeywords.value.some((item) => item.type === type)
    if (!exists) {
      updateKeyword(type, keyword)
      searchInput.value = ''
    }
  }

  const removeSearch = (index: number) => {
    const type = internalKeywords.value[index].type as keyof SearchJobExecutionFilter
    updateKeyword(type, '')
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
      @keyup.enter="addSearch"
      class="border rounded px-3 py-1 w-full text-sm"
      placeholder="Type keywords and press enter"
    />
  </div>
  <div class="flex flex-wrap gap-2 mb-4">
    <span
      v-for="(item, index) in internalKeywords"
      :key="item.type + item.keyword + index"
      class="px-2 py-1 rounded bg-blue-100 text-blue-700 text-xs flex items-center gap-1"
    >
      {{ item.type }}: {{ item.keyword }}
      <button @click="removeSearch(index)" class="text-blue-500 hover:text-blue-700">×</button>
    </span>
  </div>
</template>
