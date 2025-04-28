# SaveJobReq


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**jobName** | **string** | Name of the job | [optional] [default to undefined]
**jobDescription** | **string** | Description of the job | [optional] [default to undefined]
**jobType** | **string** | Type of the job | [default to undefined]
**active** | **boolean** | Indicates whether the job is active or not | [optional] [default to undefined]
**targetAgents** | [**Array&lt;TargetAgentReq&gt;**](TargetAgentReq.md) |  | [optional] [default to undefined]
**period** | **string** |  | [optional] [default to undefined]
**script** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { SaveJobReq } from './api';

const instance: SaveJobReq = {
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
