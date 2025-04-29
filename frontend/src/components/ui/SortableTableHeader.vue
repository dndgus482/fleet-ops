<script setup lang="ts">
  import { SortDirection, toggleSortDirection } from '@/types/search.ts'

  const props = defineProps<{
    fields: { key: string; label: string }[]
    modelValue: {
      sortField: string
      sortDirection: SortDirection
    }
  }>()

  const emit = defineEmits<{
    (e: 'update:modelValue', value: { sortField: string; sortDirection: SortDirection }): void
  }>()

  const toggleSort = (key: string) => {
    if (props.modelValue.sortField === key) {
      emit('update:modelValue', { sortField: key, sortDirection: toggleSortDirection(props.modelValue.sortDirection) })
    } else {
      emit('update:modelValue', { sortField: key, sortDirection: SortDirection.ASC })
    }
  }
</script>

<template>
  <div
    class="grid text-sm font-semibold text-gray-500 border-b pb-2"
    :style="`grid-template-columns: repeat(${props.fields.length}, minmax(0, 1fr))`"
  >
    <div
      v-for="field in fields"
      :key="field.key"
      class="px-2"
    >
      <slot
        name="column"
        :field="field"
        :isActive="props.modelValue.sortField === field.key"
        :direction="props.modelValue.sortDirection"
        :toggle="() => toggleSort(field.key)"
      >
        <!-- 기본 렌더링 -->
        <button @click="toggleSort(field.key)" class="text-left hover:text-blue-600 w-full">
          {{ field.label }}
          <span v-if="props.modelValue.sortField === field.key">
            {{ props.modelValue.sortDirection === 'ASC' ? '▲' : '▼' }}
          </span>
        </button>
      </slot>
    </div>
  </div>
</template>
