# SaveAgentGroupReq

Request DTO for saving an agent group including agents and tags.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**agentGroupName** | **string** | Name of the agent group ⚠️ Maximum 100 characters  | [optional] [default to undefined]
**agentGroupDescription** | **string** | Optional description for the agent group ⚠️ Maximum 1000 characters  | [optional] [default to undefined]
**agents** | [**Array&lt;AgentReq&gt;**](AgentReq.md) | List of agents belonging to this group ⚠️ Must not be empty  | [optional] [default to undefined]
**tags** | **Array&lt;string&gt;** | Optional list of tags associated with the group ⚠️ Can be empty or null  | [optional] [default to undefined]
**active** | **boolean** | Whether the group is active ⚠️ Defaults to true if null  | [optional] [default to undefined]

## Example

```typescript
import { SaveAgentGroupReq } from './api';

const instance: SaveAgentGroupReq = {
    agentGroupName,
    agentGroupDescription,
    agents,
    tags,
    active,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
