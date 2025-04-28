import { toRef, watch } from 'vue'
import { type MaybeRef, useUrlSearchParams } from '@vueuse/core'

export function useSearchFilter<T extends Record<string, string>>(
  inputSearchFilter: MaybeRef<T>,
  onChange: () => void,
) {
  type FilterKeys = Record<string, string>

  const searchFilter = toRef(inputSearchFilter)

  const queryParams = useUrlSearchParams('history')

  // 1. 초기 쿼리 → filter만 파싱
  const parseQueryParams = (): FilterKeys => {
    const result: FilterKeys = {}
    for (const key in searchFilter.value as FilterKeys) {
      const value = queryParams[key]
      result[key] = Array.isArray(value)
        ? value[0] ?? ''
        : value ?? ''
    }
    return result
  }

  // 2. filter → query 변환
  const syncQueryParams = (): void => {
    const filter = searchFilter.value as FilterKeys
    Object.entries(filter)
      .forEach(([k, v]) => {
        if (v != null && v !== '') {
          queryParams[k] = v
        } else {
          delete queryParams[k]
        }
      })
  }

  // 3. filter만 초기화
  searchFilter.value = parseQueryParams()

  // 4. 검색어 변경 → 쿼리 동기화 + fetchFn 호출
  watch(
    searchFilter,
    async () => {
      syncQueryParams()
      console.log('hihi')
      onChange()
    },
    { deep: true },
  )

  return { searchFilter }
}
