# SshLiveLog

Real-time log information for SSH job execution

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**agentIdx** | **number** | Index of the target agent | [default to undefined]
**lineIdx** | **number** | Index of the log line | [default to undefined]
**log** | **string** | Log content from SSH execution | [default to undefined]

## Example

```typescript
import { SshLiveLog } from './api';

const instance: SshLiveLog = {
    agentIdx,
    lineIdx,
    log,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
