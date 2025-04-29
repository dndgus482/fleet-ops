import { type SearchSort, SortDirection } from '@/types/search.ts'
import { toRef, watch } from 'vue'
import { type MaybeRef, useUrlSearchParams } from '@vueuse/core'

export function useSearchSort(
  inputSort: MaybeRef<SearchSort>,
  onChange: () => void,
) {
  const sort = toRef(inputSort)

  const queryParams = useUrlSearchParams('history')

  const changeSort = (sortField: string, sortDirection: SortDirection) => {
    sort.value = {
      sortField: sortField,
      sortDirection: sortDirection,
    }
  }

  watch(
    sort,
    async () => {
      if (sort.value.sortField) {
        queryParams.sortField = sort.value.sortField
      } else {
        delete queryParams.sortField
      }
      if (sort.value.sortDirection) {
        queryParams.sortDirection = sort.value.sortDirection
      } else {
        delete queryParams.sortDirection
      }
      onChange()
    },
    { deep: true },
  )

  return {
    sort,
    changeSort,
  }
}
