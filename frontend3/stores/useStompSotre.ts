import { defineStore } from 'pinia'
import type { SshLiveLog } from '@/types/job.ts'
import { stompClient, toJson } from '@/api/ws/wsClient.ts'
import { computed } from 'vue'
import { wsTopics } from '@/api/api.ts'

type Topic = string
type SubscribeRef = {
  ref: Symbol
  topic: Topic
  unsubscribe: () => void
}
type Handler = (data: string) => void

export const useStompStore = defineStore('stomp', () => {

  const stomp = stompClient
  const topicSubscribeMap: Map<Topic, Map<SubscribeRef, Handler>> = new Map()

  const internalSubscribe = (topic: Topic, cb: Handler) => {
    console.debug('websocket internal subscribe ', topic)
    if (!topicSubscribeMap.has(topic)) {
      topicSubscribeMap.set(topic, new Map())
      console.debug('websocket subscribe ', topic)
      stomp.subscribe(topic, (message) => {
        topicSubscribeMap.get(topic)?.forEach(fn => fn(message))
      })
    }

    const ref = {
      ref: Symbol(topic),
      topic,
      unsubscribe: () => internalUnsubscribe(ref),
    } as SubscribeRef
    topicSubscribeMap.get(topic)?.set(ref, cb)

    return ref
  }

  const internalUnsubscribe = (ref: SubscribeRef) => {
    console.debug('websocket internal unsubscribe ', ref.topic)
    const subscribeRefMap = topicSubscribeMap.get(ref.topic)
    if (!subscribeRefMap) return
    topicSubscribeMap.get(ref.topic)?.delete(ref)
    if (subscribeRefMap.size === 0) {
      console.debug('stomp unsubscribe ', ref.topic)
      stomp.unsubscribe(ref.topic)
    }
  }

  const getMapSnapshot = computed(() =>
    Object.fromEntries(topicSubscribeMap.entries())
  )

  // 🔽 api

  const subscribeJobExecutionLogs = (jobId: string, jobExecutionNo: number, cb: (_: SshLiveLog) => void) => {
    const topic = wsTopics.jobExecutionLogs(jobId, jobExecutionNo)
    return internalSubscribe(topic, toJson(cb))
  }

  const subscribeJobExecutionsChanges = (jobId: string, jobExecutionNo: number, cb: (_: string) => void) => {
    const topic = wsTopics.jobExecutionChanges(jobId, jobExecutionNo)
    return internalSubscribe(topic, cb)
  }


  return {
    getMapSnapshot,
    subscribeJobExecutionLogs,
    subscribeJobExecutionsChanges,
    disconnect: stomp.disconnect,
    isConnected: stomp.isConnected,
  }
})
