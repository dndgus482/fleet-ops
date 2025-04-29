<script setup lang="ts">
import { useConfirmDialog } from '@vueuse/core'

defineProps({
  message: {
    type: String,
    required: true
  }
})

const dialog = useConfirmDialog()

defineExpose({
  ...dialog
})
</script>

<template>
  <div v-if="dialog.isRevealed.value"
       class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-sm w-full">
      <p class="text-center text-lg mb-4">{{ message }}</p>
      <div class="flex justify-center gap-4">
        <button class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                @click="dialog.confirm">Yes
        </button>
        <button class="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400"
                @click="dialog.cancel">No
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
