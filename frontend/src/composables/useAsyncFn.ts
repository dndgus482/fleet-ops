import { ref } from 'vue'

export function useAsyncFn<Args extends unknown[] = []>(
  fn: (...args: Args) => Promise<void>,
) {
  const isLoading = ref(false)
  const isReady = ref(false)
  const error = ref<unknown>(null)

  const execute = async (...args: Args) => {
    isLoading.value = true
    isReady.value = false
    error.value = null
    try {
      await fn(...args)
      isReady.value = true
    } catch (err) {
      error.value = err
    } finally {
      isLoading.value = false
    }
  }

  return { isLoading, isReady, error, execute }
}
