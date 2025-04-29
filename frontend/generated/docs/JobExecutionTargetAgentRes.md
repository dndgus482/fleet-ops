# JobExecutionTargetAgentRes

Response containing details about a target agent involved in job execution

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ip** | **string** | IP address of the agent | [default to undefined]
**userName** | **string** | Username of the agent | [default to undefined]
**startDateTime** | **string** | Date and time when the agent started the execution | [default to undefined]
**endDateTime** | **string** | Date and time when the agent finished the execution | [default to undefined]
**log** | **string** | Log details specific to this agent\&#39;s execution | [default to undefined]

## Example

```typescript
import { JobExecutionTargetAgentRes } from './api';

const instance: JobExecutionTargetAgentRes = {
    ip,
    userName,
    startDateTime,
    endDateTime,
    log,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
