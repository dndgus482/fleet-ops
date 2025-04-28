# JobExecutionSearchReq

Request to search job executions based on various criteria

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**sortField** | **string** | sort field | [optional] [default to undefined]
**sortDirection** | **string** | sort direction | [optional] [default to undefined]
**sort** | **any** |  | [optional] [default to undefined]
**pageToken** | **string** | null if first search | [optional] [default to undefined]
**maxPageSize** | **number** | max size to get | [optional] [default to undefined]
**page** | **any** |  | [optional] [default to undefined]
**jobId** | **string** | Identifier of the job to filter by | [optional] [default to undefined]
**agentGroupId** | **string** | Agent group ID to filter by | [optional] [default to undefined]
**ip** | **string** | IP address of the agent, nullable if not applicable | [optional] [default to undefined]

## Example

```typescript
import { JobExecutionSearchReq } from './api';

const instance: JobExecutionSearchReq = {
    sortField,
    sortDirection,
    sort,
    pageToken,
    maxPageSize,
    page,
    jobId,
    agentGroupId,
    ip,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
