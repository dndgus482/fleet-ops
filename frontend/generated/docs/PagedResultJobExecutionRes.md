# PagedResultJobExecutionRes


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**results** | [**Array&lt;JobExecutionRes&gt;**](JobExecutionRes.md) | results of search | [default to undefined]
**nextPageToken** | **string** | use this token for the next page | [optional] [default to undefined]
**totalCount** | **number** | total count of matched search results | [default to undefined]

## Example

```typescript
import { PagedResultJobExecutionRes } from './api';

const instance: PagedResultJobExecutionRes = {
    results,
    nextPageToken,
    totalCount,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
