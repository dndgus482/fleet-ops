import { computed, ref, watchEffect } from 'vue'

export enum DataMode {
  NEW = 'NEW',
  EXISTING = 'EXISTING'
}

export function useDataMode(newCondition: () => boolean) {
  const mode = ref<DataMode>(DataMode.NEW)

  watchEffect(() => {
    if (newCondition()) mode.value = DataMode.NEW
    else mode.value = DataMode.EXISTING
  })

  const isNewMode = computed(() => {
    return mode.value === DataMode.NEW
  })
  const isExistingMode = computed(() => {
    return mode.value === DataMode.EXISTING
  })

  return { mode, isNewMode, isExistingMode }
}
