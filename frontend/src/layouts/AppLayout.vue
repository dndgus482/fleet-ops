<script setup lang="ts">
  import AppNavigation from '@/layouts/AppNavigation.vue'
  import AppHeader from '@/layouts/AppHeader.vue'
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <header class="shadow-sm px-6">
      <app-header />
    </header>
    <div class="flex flex-1 overflow-hidden px-6">
      <aside class="flex-1 overflow-y-auto">
        <app-navigation />
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
