import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

export function useBreadcrumb() {
  const route = useRoute()
  const router = useRouter()

  function buildBreadcrumb(r) {
    const breadcrumbs = []

    while (r) {
      const raw = r.meta?.breadcrumb
      const label = typeof raw === 'function' ? raw(route) : raw
      if (label) {
        breadcrumbs.unshift({
          label,
          to: router.resolve({ name: r.name, params: route.params }).fullPath,
        })
      }
      const parentName = r.meta?.parent
      r = parentName ? router.getRoutes().find(rt => rt.name === parentName) : null
    }

    return breadcrumbs
  }

  return computed(() => {
    const current = router.getRoutes().find(r => r.name === route.name)
    return current ? buildBreadcrumb(current) : []
  })
}