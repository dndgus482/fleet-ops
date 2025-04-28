# JobSearchReq


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**sortField** | **string** | sort field | [optional] [default to undefined]
**sortDirection** | **string** | sort direction | [optional] [default to undefined]
**sort** | **any** |  | [optional] [default to undefined]
**pageToken** | **string** | null if first search | [optional] [default to undefined]
**maxPageSize** | **number** | max size to get | [optional] [default to undefined]
**page** | **any** |  | [optional] [default to undefined]
**jobName** | **string** | job name to filter by | [optional] [default to undefined]

## Example

```typescript
import { JobSearchReq } from './api';

const instance: JobSearchReq = {
    sortField,
    sortDirection,
    sort,
    pageToken,
    maxPageSize,
    page,
    jobName,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
