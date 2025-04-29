export function debounce<T extends (...args: any[]) => void>(func: T, wait = 300) {
  let timeout: ReturnType<typeof setTimeout> | null

  return function(...args: Parameters<T>) {
    if (timeout) clearTimeout(timeout)
    timeout = setTimeout(() => func(...args), wait)
  }
}
