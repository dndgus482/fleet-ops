<script setup lang="ts">
  /**
   * ResponsiveGrid.vue
   *
   * A flexible grid layout component designed for responsive design.
   *
   * Usage:
   * <ResponsiveGrid>
   *   <div>Item 1</div>
   *   <div>Item 2</div>
   *   <div>Item 3</div>
   * </ResponsiveGrid>
   *
   * Features:
   * - Automatically adjusts the column layout based on screen size.
   */
</script>

<template>
  <div class="grid gap-6" style="grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
    <slot />
  </div>
</template>
