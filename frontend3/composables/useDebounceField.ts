import { useField } from 'vee-validate'
import { watchDebounced } from '@vueuse/core'

export function useDebounceField<TValue = unknown>(
  path: MaybeRefOrGetter<string>) {
  const {
    value,
    errorMessage,
    validate,
    setErrors,
  } = useField<TValue>(path, undefined, { validateOnValueUpdate: false })

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