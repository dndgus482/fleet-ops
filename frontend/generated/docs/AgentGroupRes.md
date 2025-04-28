# AgentGroupRes

Agent group response object

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**agentGroupId** | **string** | ID of the agent group | [default to undefined]
**agentGroupName** | **string** | Name of the agent group | [default to undefined]
**agentGroupDescription** | **string** | Description of the agent group | [default to undefined]
**agents** | [**Array&lt;AgentRes&gt;**](AgentRes.md) | List of agents in the group | [default to undefined]
**tags** | **Array&lt;string&gt;** | List of tags associated with the group | [default to undefined]
**active** | **boolean** | Whether the group is active | [default to undefined]

## Example

```typescript
import { AgentGroupRes } from './api';

const instance: AgentGroupRes = {
    agentGroupId,
    agentGroupName,
    agentGroupDescription,
    agents,
    tags,
    active,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
