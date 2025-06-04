import { computed, type ComputedRef, ref, watchEffect } from 'vue'

export enum DataMode {
  NEW = 'NEW',
  EXISTING = 'EXISTING',
}

export function useDataMode(newCondition: ComputedRef<boolean>) {
  const mode = ref<DataMode>(
    newCondition.value ? DataMode.NEW : DataMode.EXISTING,
  )

  watchEffect(() => {
    mode.value = newCondition.value ? DataMode.NEW : DataMode.EXISTING
  })

  const isNewMode = computed(() => {
    return mode.value === DataMode.NEW
  })
  const isExistingMode = computed(() => {
    return mode.value === DataMode.EXISTING
  })

  return { mode, isNewMode, isExistingMode }
}
