<script setup lang="ts">
  const { message, no, yes } = defineProps<{
    message: string,
    no?: () => {},
    yes: () => {},
  }>()

  const { closeAll } = useOverlay()

  const pressYes = () => {
    closeAll()
    yes()
  }

  const pressNo = () => {
    closeAll()
    no?.()
  }
</script>

<template>
  <UModal :title="message">
    <template #body>
      <div class="flex justify-end gap-2">
        <UButton label="No" color="neutral" variant="soft" @click="pressNo" />
        <UButton label="Yes" color="secondary" variant="soft" @click="pressYes" />
      </div>
    </template>
  </UModal>
</template>
