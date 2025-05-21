<script setup lang="ts" xmlns="http://www.w3.org/1999/html">
  import AgentDetail from '~/components/AgentDetail.vue'
  import AgentLinkedJobs from '~/components/AgentLinkedJobs.vue'

  const route = useRoute()
  const ip = route.params.ip as string
  const userName = route.params.userName as string
</script>

<template>
  <main>
    <div class="flex flex-col">
      <AgentDetail :ip="ip" :userName="userName" />
      <AgentLinkedJobs />
    </div>
  </main>
</template>
