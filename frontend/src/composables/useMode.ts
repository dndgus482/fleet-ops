import { computed, ref, watchEffect } from 'vue'

export enum Mode {
  CREATE = 'CREATE',
  UPDATE = 'UPDATE',
  READ = 'READ'
}

type ModeParams = {
  createCondition: () => boolean;
  updateCondition: () => boolean;
}


export function useMode({ createCondition, updateCondition }: ModeParams) {
  const mode = ref<Mode | null>(null)

  watchEffect(() => {
    if (createCondition()) mode.value = Mode.CREATE
    else if (updateCondition()) mode.value = Mode.UPDATE
    else mode.value = Mode.READ
  })

  const isCreateMode = computed(() => {return mode.value === Mode.CREATE})
  const isUpdateMode = computed(() => {return mode.value === Mode.UPDATE})
  const isReadMode = computed(() => {return mode.value === Mode.READ})

  return { isCreateMode, isUpdateMode, isReadMode }
}
