<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="min-h-screen flex bg-gray-100">
    <div class="flex w-full min-h-screen bg-white shadow-lg rounded-lg overflow-auto">
      <!-- Sidebar -->
      <aside class="w-64 bg-gray-800 text-white p-6">
        <div class="flex items-center space-x-2 mb-6">
          <span class="text-blue-400 text-2xl font-bold">FleetOps</span>
        </div>
        <nav>
          <RouterLink to="/jobs" class="block py-2 px-4 hover:bg-gray-700 rounded">Jobs</RouterLink>
          <RouterLink to="/agentGroups" class="block py-2 px-4 hover:bg-gray-700 rounded">Agent Groups</RouterLink>
        </nav>
      </aside>

      <!-- Main Content -->
      <div class="flex-1 p-6 overflow-auto">
        <RouterView />
      </div>
    </div>
  </div>
</template>
