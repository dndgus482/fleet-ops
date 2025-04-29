# JobHistoryRes

Response containing details of a job\'s execution history

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**jobId** | **string** | Unique identifier of the job | [default to undefined]
**jobHistoryNo** | **number** | Unique number for the job history | [default to undefined]
**jobName** | **string** | Name of the job | [default to undefined]
**jobType** | **string** | Type of the job | [default to undefined]
**history** | **{ [key: string]: object; }** | Additional history details of the job represented as key-value pairs | [default to undefined]
**regUserId** | **string** | User ID of the person who registered the job | [default to undefined]
**regDateTime** | **string** | Date and time when the job was registered | [default to undefined]

## Example

```typescript
import { JobHistoryRes } from './api';

const instance: JobHistoryRes = {
    jobId,
    jobHistoryNo,
    jobName,
    jobType,
    history,
    regUserId,
    regDateTime,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
