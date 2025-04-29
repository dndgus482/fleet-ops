export const formatDateTime = (dateString?: string) => {
  if (!dateString) return ''

  const date = new Date(dateString)
  if (isNaN(date.getTime())) return ''

  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  }).format(date)
}

export const toRelativeTime = (dateString?: string) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  const now = new Date()
  const diff = Math.floor((now.getTime() - date.getTime()) / 1000) // 초 단위 차이

  const rtf = new Intl.RelativeTimeFormat('en', { numeric: 'auto' })

  if (diff < 60) return 'just now'
  if (diff < 3600) return rtf.format(-Math.floor(diff / 60), 'minute')
  if (diff < 86400) return rtf.format(-Math.floor(diff / 3600), 'hour')
  if (diff < 604800) return rtf.format(-Math.floor(diff / 86400), 'day')
  if (diff < 2592000) return rtf.format(-Math.floor(diff / 604800), 'week')

  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  }).format(date)
}

export const toRelativeDuration = (start?: string, end?: string) => {
  if (!start || !end) return ''
  const startTime = new Date(start).getTime()
  const endTime = new Date(end).getTime()
  const diffInSeconds = Math.floor((endTime - startTime) / 1000)

  if (diffInSeconds < 60) return `${diffInSeconds} sec`
  if (diffInSeconds < 3600) return `${Math.floor(diffInSeconds / 60)} min`
  if (diffInSeconds < 86400) return `${Math.floor(diffInSeconds / 3600)} hr`

  return `${Math.floor(diffInSeconds / 86400)} days`
}
