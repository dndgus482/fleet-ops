import { ref } from 'vue'

export function useValidation<T extends (...args: any[]) => string | undefined>(validateFn: T) {
  const errorMessage = ref<string | undefined>()

  const validate = ((...args: Parameters<T>) => {
    const error = validateFn(...args)
    errorMessage.value = error
    return error === undefined
  })

  return { errorMessage, validate }
}