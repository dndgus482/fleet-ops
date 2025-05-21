import axios from 'axios'

export default defineNuxtPlugin(() => {
  const instance = axios.create({
    baseURL: useRuntimeConfig().public.apiBase,
  })
  return {
    provide: {
      axios: instance,
    },
  }
})