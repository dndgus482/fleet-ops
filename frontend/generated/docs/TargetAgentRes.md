# TargetAgentRes

Response containing details of a target agent

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**targetAgentType** | **string** | Type of the target agent | [default to undefined]
**agentGroupId** | **string** | Group ID of the target agents, if applicable | [optional] [default to undefined]
**agentGroupName** | **string** | Group name of the target agents, if applicable | [optional] [default to undefined]
**ip** | **string** | IP address of the target agent | [optional] [default to undefined]
**userName** | **string** | Username associated with the target agent | [optional] [default to undefined]

## Example

```typescript
import { TargetAgentRes } from './api';

const instance: TargetAgentRes = {
    targetAgentType,
    agentGroupId,
    agentGroupName,
    ip,
    userName,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
