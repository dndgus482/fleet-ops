<script setup lang="ts">
  import AppNavigation from '~/components/layout/AppNavigation.vue'
  import AppHeader from '~/components/layout/AppHeader.vue'
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <header class="shadow-sm px-6">
      <AppHeader />
    </header>
    <div class="flex flex-1 overflow-hidden px-6">
      <aside class="p-4 overflow-y-auto">
        <AppNavigation />
      </aside>
      <main class="flex-4 p-6 overflow-y-auto">
        <slot />
      </main>
      <div class="flex-1">
        <!-- empty space -->
      </div>
    </div>
  </div>
</template>