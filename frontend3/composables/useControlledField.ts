import { useField } from 'vee-validate'

export function useControlledField<TValue = unknown>(
  path: MaybeRefOrGetter<string>) {
  const {
    value,
    errorMessage,
    validate,
    setErrors,
  } = useField<TValue>(path, undefined, { validateOnValueUpdate: false })

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