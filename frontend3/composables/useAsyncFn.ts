import { ref } from 'vue'

export function useAsyncFn(fn: () => Promise<void>) {
  const isLoading = ref(false)
  const isReady = ref(false)
  const error = ref<unknown>(null)

  const execute = async () => {
    isLoading.value = true
    isReady.value = false
    error.value = null
    try {
      await fn()
      isReady.value = true
    } catch (err) {
      error.value = err
    } finally {
      isLoading.value = false
    }
  }

  return { isLoading, isReady, error, execute }
}