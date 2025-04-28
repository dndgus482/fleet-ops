# JobExecutionApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**executeJob**](#executejob) | **POST** /api/jobs/{jobId}/execute | Execute a job|
|[**getJobExecutionById**](#getjobexecutionbyid) | **GET** /api/jobs/{jobId}/executions/{jobExecutionNo} | Get job execution by ID|
|[**getLiveLog**](#getlivelog) | **GET** /api/jobs/{jobId}/executions/{jobExecutionNo}/logs | Get live logs for job execution|
|[**searchJobExecution**](#searchjobexecution) | **POST** /api/jobExecution/search | Search job executions|
|[**stopJobExecution**](#stopjobexecution) | **POST** /api/jobs/{jobId}/executions/{jobExecutionNo}/stop | Stop job execution|

# **executeJob**
> JobExecuteRes executeJob()

Executes the specified job by ID

### Example

```typescript
import {
    JobExecutionApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobExecutionApi(configuration);

let jobId: string; // (default to undefined)

const { status, data } = await apiInstance.executeJob(
    jobId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|


### Return type

**JobExecuteRes**

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

# **getJobExecutionById**
> JobExecutionRes getJobExecutionById()

Retrieve details of a specific job execution

### Example

```typescript
import {
    JobExecutionApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobExecutionApi(configuration);

let jobId: string; // (default to undefined)
let jobExecutionNo: number; // (default to undefined)

const { status, data } = await apiInstance.getJobExecutionById(
    jobId,
    jobExecutionNo
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|
| **jobExecutionNo** | [**number**] |  | defaults to undefined|


### Return type

**JobExecutionRes**

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

# **getLiveLog**
> Array<SshLiveLog> getLiveLog()

Fetch live logs for the specified job execution

### Example

```typescript
import {
    JobExecutionApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobExecutionApi(configuration);

let jobId: string; // (default to undefined)
let jobExecutionNo: number; // (default to undefined)

const { status, data } = await apiInstance.getLiveLog(
    jobId,
    jobExecutionNo
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|
| **jobExecutionNo** | [**number**] |  | defaults to undefined|


### Return type

**Array<SshLiveLog>**

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

# **searchJobExecution**
> PagedResultJobExecutionRes searchJobExecution(jobExecutionSearchReq)

Search job executions based on given criteria

### Example

```typescript
import {
    JobExecutionApi,
    Configuration,
    JobExecutionSearchReq
} from './api';

const configuration = new Configuration();
const apiInstance = new JobExecutionApi(configuration);

let jobExecutionSearchReq: JobExecutionSearchReq; //Search filters

const { status, data } = await apiInstance.searchJobExecution(
    jobExecutionSearchReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobExecutionSearchReq** | **JobExecutionSearchReq**| Search filters | |


### Return type

**PagedResultJobExecutionRes**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

# **stopJobExecution**
> stopJobExecution()

Stops the specified job execution

### Example

```typescript
import {
    JobExecutionApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobExecutionApi(configuration);

let jobId: string; // (default to undefined)
let jobExecutionNo: number; // (default to undefined)

const { status, data } = await apiInstance.stopJobExecution(
    jobId,
    jobExecutionNo
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|
| **jobExecutionNo** | [**number**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**404** | Not Found |  -  |
|**409** | Conflict |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

