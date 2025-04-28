# JobRes

Response containing details of a job

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**jobId** | **string** | Unique identifier of the job | [default to undefined]
**jobName** | **string** | Name of the job | [default to undefined]
**jobDescription** | **string** | Description of the job | [default to undefined]
**jobType** | **string** | Type of the job | [default to undefined]
**active** | **boolean** | Indicates whether the job is active or not | [default to undefined]
**targetAgents** | [**Array&lt;TargetAgentRes&gt;**](TargetAgentRes.md) | List of target agents assigned to the job | [optional] [default to undefined]
**period** | **string** | Execution period of the job in cron format | [optional] [default to undefined]
**script** | **string** | Script associated with the job | [optional] [default to undefined]

## Example

```typescript
import { JobRes } from './api';

const instance: JobRes = {
    jobId,
    jobName,
    jobDescription,
    jobType,
    active,
    targetAgents,
    period,
    script,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
