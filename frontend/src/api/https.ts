const BASE_URL = import.meta.env.VITE_API_URL
import qs from 'qs'

async function httpRequest<T>(method: string, endpoint: string, body?: unknown, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${BASE_URL}${endpoint}`, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers
    },
    body: body ? JSON.stringify(body) : undefined,
    ...options
  })

  if (!response.ok) {
    throw new Error(`API Error: ${response.status} ${response.statusText} (URL: ${endpoint})`)
  }

  const contentType = response.headers.get('Content-Type')
  if (contentType && contentType.includes('application/json')) {
    const jsonResponse = await response.json()
    return jsonResponse as T
  }

  return {} as T
}

export function httpGet<T>(endpoint: string, query?: Record<string, any>, options: RequestInit = {}) {
  const queryString = query ? `?${qs.stringify(query, { arrayFormat: 'repeat', allowDots: true })}` : ''
  return httpRequest<T>('GET',  `${endpoint}${queryString}`, undefined, options)
}

export function httpPost<T>(endpoint: string, body: unknown = null, options: RequestInit = {}) {
  return httpRequest<T>('POST', endpoint, body, options)
}

export function httpPut<T>(endpoint: string, body: unknown = null, options: RequestInit = {}) {
  return httpRequest<T>('PUT', endpoint, body, options)
}

export function httpDelete<T>(endpoint: string, options: RequestInit = {}) {
  return httpRequest<T>('DELETE', endpoint, undefined, options)
}
