<script setup lang="ts">
import { computed, ref } from 'vue'
import type {
  RowData,
  TableColumns,
  CreateRowProps,
} from 'naive-ui/es/data-table/src/interface'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'

type NonEmptyArray<T> = [T, ...T[]]

const { searchableKeys, columns, rowProps, data } = defineProps<{
  // https://www.naiveui.com/en-US/light/components/data-table
  searchableKeys: NonEmptyArray<string>
  columns: TableColumns<any>
  rowProps: CreateRowProps<any>
  data: RowData[]
}>()

const emit = defineEmits<{
  (e: 'search', searchKey: string, searchValue: string): void
}>()

const selectedType = ref(searchableKeys[0])
const searchInput = ref('')

const searchableTypes = computed(() => {
  return searchableKeys.map((key) => ({
    label: key,
    value: key,
  }))
})

const search = async () => {
  emit('search', selectedType.value, searchInput.value)
}


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
    <base-icon-button icon="lucide-search" @click="search" />
  </div>
  <n-data-table
    ref="table"
    :columns="columns"
    :data="data"
    :row-props="rowProps"
  />
</template>
