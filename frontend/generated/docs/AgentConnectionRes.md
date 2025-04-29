# AgentConnectionRes

Response object representing agent connection information

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ip** | **string** | IP address of the agent | [default to undefined]
**userName** | **string** | Username of the agent | [default to undefined]
**connected** | **boolean** | Connection status | [default to undefined]
**log** | **string** | Connection log details | [default to undefined]

## Example

```typescript
import { AgentConnectionRes } from './api';

const instance: AgentConnectionRes = {
    ip,
    userName,
    connected,
    log,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
