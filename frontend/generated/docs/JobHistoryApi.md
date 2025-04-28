# JobHistoryApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getJobHistoryById**](#getjobhistorybyid) | **GET** /api/jobs/{jobId}/history/{jobHistoryNo} | Get job history details|
|[**getJobHistoryByJobId**](#getjobhistorybyjobid) | **GET** /api/jobs/{jobId}/history | Get job history by job ID|

# **getJobHistoryById**
> JobHistoryRes getJobHistoryById()

Retrieve the details of a specific job history entry

### Example

```typescript
import {
    JobHistoryApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobHistoryApi(configuration);

let jobId: string; // (default to undefined)
let jobHistoryNo: number; // (default to undefined)

const { status, data } = await apiInstance.getJobHistoryById(
    jobId,
    jobHistoryNo
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|
| **jobHistoryNo** | [**number**] |  | defaults to undefined|


### Return type

**JobHistoryRes**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**404** | Not Found |  -  |
|**409** | Conflict |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getJobHistoryByJobId**
> Array<JobHistoryRes> getJobHistoryByJobId()

Retrieve the history of job executions based on job ID

### Example

```typescript
import {
    JobHistoryApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobHistoryApi(configuration);

let jobId: string; // (default to undefined)

const { status, data } = await apiInstance.getJobHistoryByJobId(
    jobId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|


### Return type

**Array<JobHistoryRes>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**404** | Not Found |  -  |
|**409** | Conflict |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

