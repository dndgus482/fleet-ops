import { type FieldOptions, type RuleExpression, useField } from 'vee-validate'
import { watchDebounced } from '@vueuse/core'
import { type MaybeRef, type MaybeRefOrGetter, watch } from 'vue'

export function useDebounceField<TValue = unknown>(
  path: MaybeRefOrGetter<string>,
  rules?: MaybeRef<RuleExpression<TValue>>,
  opts?: Partial<FieldOptions<TValue>>) {
  const {
    value,
    errorMessage,
    validate,
    setErrors,
  } = useField<TValue>(path, rules, { ...opts, validateOnValueUpdate: false })

  watchDebounced(
    value,
    async () => {
      await validate()
    },
    { debounce: 1000 },
  )

  watch(value, () => {
    if (errorMessage.value) {
      setErrors([])
    }
  })

  return {
    value,
    errorMessage,
    validate,
    setErrors,
  }

}