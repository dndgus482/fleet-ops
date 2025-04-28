# JobExecutionRes

Response containing detailed information about a specific job execution.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**jobId** | **string** | Identifier of the job | [default to undefined]
**jobName** | **string** | Name of the job | [default to undefined]
**jobExecutionNo** | **number** | Execution number of the job execution | [default to undefined]
**jobHistoryNo** | **number** | Job history number linked to the execution | [default to undefined]
**jobExecutionStatus** | **string** | Current status of the job execution | [default to undefined]
**executionUserId** | **string** | User ID of the user who executed the job | [default to undefined]
**executionDateTime** | **string** | Date and time when the job was executed | [default to undefined]
**startDateTime** | **string** | Date and time when the execution started | [optional] [default to undefined]
**endDateTime** | **string** | Date and time when the execution ended | [optional] [default to undefined]
**targetAgents** | [**Array&lt;JobExecutionTargetAgentRes&gt;**](JobExecutionTargetAgentRes.md) | List of target agents involved in this job execution | [default to undefined]

## Example

```typescript
import { JobExecutionRes } from './api';

const instance: JobExecutionRes = {
    jobId,
    jobName,
    jobExecutionNo,
    jobHistoryNo,
    jobExecutionStatus,
    executionUserId,
    executionDateTime,
    startDateTime,
    endDateTime,
    targetAgents,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
