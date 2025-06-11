<script setup lang="ts">
import { computed, ref } from 'vue'
import type {
  RowData,
  TableColumns,
  CreateRowProps,
} from 'naive-ui/es/data-table/src/interface'

type NonEmptyArray<T> = [T, ...T[]]

const { searchableKeys, columns, rowProps, data } = defineProps<{
  // https://www.naiveui.com/en-US/light/components/data-table
  searchableKeys: NonEmptyArray<string>
  columns: TableColumns<any>
  rowProps: CreateRowProps<any>
  data: RowData[]
}>()

const selectedType = ref(searchableKeys[0])
const searchInput = ref('')

const searchableTypes = computed(() => {
  return searchableKeys.map((key) => ({
    label: key,
    value: key,
  }))
})

const searchResult = computed(() => {
  const keyword = searchInput.value.trim().toLowerCase()

  return data.filter((row) => {
    const value = (row[selectedType.value] || '').toString().trim()
    return value.toLowerCase().includes(keyword)
  })
})
</script>

<template>
  <div class="mb-4 flex items-center gap-2">
    <n-select
      style="min-width: 200px; width: auto"
      v-model:value="selectedType"
      :options="searchableTypes"
    >
    </n-select>
    <n-input v-model:value="searchInput" clearable />
  </div>
  <n-data-table
    ref="table"
    :columns="columns"
    :data="searchResult"
    :row-props="rowProps"
  />
</template>
