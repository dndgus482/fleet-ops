export interface Pageable {
  pageToken?: string
  maxPageSize: number
}

export interface SearchSort {
  sortField: string
  sortDirection: SortDirection
}

export enum SortDirection {
  ASC = 'ASC',
  DESC = 'DESC',
}

export const toggleSortDirection = (sortDirection: SortDirection) => {
  return sortDirection === SortDirection.ASC ? SortDirection.DESC : SortDirection.ASC
}


export interface PagedResult<T> {
  results: T[]
  totalCount: number
  nextPageToken: string
}


