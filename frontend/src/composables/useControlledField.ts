import { type MaybeRef, type MaybeRefOrGetter, watch } from 'vue'
import { type FieldContext, type FieldOptions, type RuleExpression, useField } from 'vee-validate'

export function useControlledField<TValue = unknown>(
  path: MaybeRefOrGetter<string>,
  rules?: MaybeRef<RuleExpression<TValue>>,
  opts?: Partial<FieldOptions<TValue>>) {
  const {
    value,
    errorMessage,
    validate,
    setErrors,
  } = useField<TValue>(path, rules, { ...opts, validateOnValueUpdate: false })

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