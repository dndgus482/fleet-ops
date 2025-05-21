import { createStompClient } from './websocket.ts'

export const stompClient = createStompClient({
  url: import.meta.env.VITE_WS_URL,
  reconnectDelay: 5000,
})


// subscription json parse helper
export function toJson<T>(cb: (data: T) => void) {
  return (raw: string) => {
    try {
      const parsed = JSON.parse(raw)
      cb(parsed as T)
    } catch (e) {
      console.warn('❌ Failed to parse STOMP message', raw)
    }
  }
}
