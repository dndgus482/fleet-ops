import { computed, type Ref } from 'vue'
import { DataMode } from '@/composables/useDataMode'

export enum FormMode {
  CREATE = 'CREATE',
  UPDATE = 'UPDATE',
  READ = 'READ'
}

export function useFormMode({ dataMode, updateFlag }: {
  dataMode: DataMode,
  updateFlag: Ref<boolean>
}) {
  const mode = computed(() => {
    if (dataMode === DataMode.NEW) return FormMode.CREATE
    return updateFlag.value ? FormMode.UPDATE : FormMode.READ
  })

  return {
    formMode: mode,
    isCreateMode: computed(() => mode.value === FormMode.CREATE),
    isReadMode: computed(() => mode.value === FormMode.READ),
    isUpdateMode: computed(() => mode.value === FormMode.UPDATE),
  }
}