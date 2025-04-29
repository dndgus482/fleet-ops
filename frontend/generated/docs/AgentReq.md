# AgentReq

Request object containing agent connection information 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ip** | **string** | IP address of the agent in IPv4 format  | [optional] [default to undefined]
**userName** | **string** | Username for SSH login (1–100 characters allowed) ⚠️ Maximum 100 characters  | [optional] [default to undefined]

## Example

```typescript
import { AgentReq } from './api';

const instance: AgentReq = {
    ip,
    userName,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
