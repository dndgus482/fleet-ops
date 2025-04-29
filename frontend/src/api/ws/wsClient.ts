import { createStompClient } from "./websocket.ts"

const stompClient = createStompClient({
  url: import.meta.env.VITE_WS_URL,
  reconnectDelay: 5000
})


// 공통 JSON 파싱 구독 헬퍼
export function subscribeJson<T>(topic: string, cb: (data: T) => void) {
  stompClient.subscribe(topic, (raw) => {
    try {
      const parsed = JSON.parse(raw)
      cb(parsed as T)
    } catch (e) {
      console.warn('❌ Failed to parse STOMP message', raw)
    }
  })
}

export function subscribe<T>(topic: string, cb: (data: T) => void) {
  stompClient.subscribe(topic, (raw) => {cb(raw as T)})
}
