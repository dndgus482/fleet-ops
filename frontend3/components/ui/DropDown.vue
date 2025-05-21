<script lang="ts" setup>
import { ref, computed, defineProps, defineEmits } from 'vue';
import { onClickOutside } from '@vueuse/core'

interface Option {
  label: string;
  value: string | number;
}

const props = defineProps<{
  modelValue: string | number;
  options: Option[];
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: string | number): void;
}>();

const isOpen = ref(false);
const dropdownRef = ref<HTMLElement | null>(null);

const selectedLabel = computed(() => {
  const selected = props.options.find(opt => opt.value === props.modelValue);
  return selected ? selected.label : 'Select an option';
});

const selectOption = (option: Option) => {
  emit('update:modelValue', option.value);
  isOpen.value = false;
};

onClickOutside(dropdownRef, () => {
  isOpen.value = false;
});
</script>


<template>
  <div class="relative w-64" ref="dropdownRef">
    <button
      class="border rounded-lg p-2 w-full text-left bg-white flex justify-between items-center"
      @click="isOpen = !isOpen"
    >
      <span>{{ selectedLabel }}</span>
      <span class="text-gray-500">▼</span>
    </button>
    <ul
      v-if="isOpen"
      class="absolute mt-1 w-full bg-white border rounded-lg shadow-lg z-10"
    >
      <li
        v-for="option in options"
        :key="option.value"
        class="p-2 hover:bg-gray-200 cursor-pointer"
        @click="selectOption(option)"
      >
        {{ option.label }}
      </li>
    </ul>
  </div>
</template>

<style scoped>

</style>
