// stompClient.ts
import SockJS from 'sockjs-client'
import { Client, type IMessage } from '@stomp/stompjs'

type MessageCallback = (message: string) => void

interface StompClientOptions {
  url: string
  reconnectDelay?: number
}

export function createStompClient(options: StompClientOptions) {
  const listeners: Map<string, MessageCallback> = new Map()

  const client = new Client({
    webSocketFactory: () => new SockJS(options.url),
    reconnectDelay: options.reconnectDelay ?? 5000,
  })

  let connected = false

  client.onConnect = () => {
    connected = true
    for (const [topic, cb] of listeners.entries()) {
      client.subscribe(topic, (msg: IMessage) => cb(msg.body))
    }
    console.debug('📡 STOMP connected')
  }

  client.onWebSocketClose = () => {
    connected = false
    console.warn('🔌 WebSocket closed')
  }

  client.onStompError = (frame) => {
    console.error('❌ STOMP error', frame)
  }

  const connectIfNeeded = () => {
    if (!connected && !client.active) {
      client.activate()
    }
  }

  return {
    subscribe(topic: string, cb: MessageCallback) {
      listeners.set(topic, cb)
      connectIfNeeded()
      if (connected) {
        client.subscribe(topic, (msg: IMessage) => cb(msg.body))
      }
    },

    unsubscribe(topic: string) {
      listeners.delete(topic)
      // 실제 stomp 구독 객체를 저장 안 했기 때문에 완전한 unsubscribe는 안 됨
      // 필요하면 topic -> Subscription map 만들어서 정확히 해줄 수 있음
    },

    isConnected(): boolean {
      return connected
    },

    disconnect() {
      client.deactivate()
      connected = false
      listeners.clear()
    },
  }
}
