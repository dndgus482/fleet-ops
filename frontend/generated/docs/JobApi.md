# JobApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**createJob**](#createjob) | **POST** /api/jobs | Create a new job|
|[**deleteJobById**](#deletejobbyid) | **DELETE** /api/jobs/{jobId} | Delete a job|
|[**getJobById**](#getjobbyid) | **GET** /api/jobs/{jobId} | Get job by ID|
|[**searchJob**](#searchjob) | **GET** /api/job/search | Retrieve all jobs|
|[**switchActiveJob**](#switchactivejob) | **POST** /api/jobs/{jobId}/switchActive | Switch job active status|
|[**updateJob**](#updatejob) | **PUT** /api/jobs/{jobId} | Update a job|

# **createJob**
> JobRes createJob(saveJobReq)

Add a new job to the system

### Example

```typescript
import {
    JobApi,
    Configuration,
    SaveJobReq
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let saveJobReq: SaveJobReq; //Job details

const { status, data } = await apiInstance.createJob(
    saveJobReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **saveJobReq** | **SaveJobReq**| Job details | |


### Return type

**JobRes**

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

# **deleteJobById**
> deleteJobById()

Remove a job from the system

### Example

```typescript
import {
    JobApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let jobId: string; // (default to undefined)

const { status, data } = await apiInstance.deleteJobById(
    jobId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|


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

# **getJobById**
> JobRes getJobById()

Retrieve specific job details

### Example

```typescript
import {
    JobApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let jobId: string; // (default to undefined)

const { status, data } = await apiInstance.getJobById(
    jobId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **jobId** | [**string**] |  | defaults to undefined|


### Return type

**JobRes**

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

# **searchJob**
> PagedResultJobRes searchJob()

Search for jobs based on criteria

### Example

```typescript
import {
    JobApi,
    Configuration,
    JobSearchReq
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let req: JobSearchReq; // (default to undefined)

const { status, data } = await apiInstance.searchJob(
    req
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **req** | **JobSearchReq** |  | defaults to undefined|


### Return type

**PagedResultJobRes**

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

# **switchActiveJob**
> switchActiveJob(switchActiveReq)

Enable or disable the active status of a job

### Example

```typescript
import {
    JobApi,
    Configuration,
    SwitchActiveReq
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let jobId: string; // (default to undefined)
let switchActiveReq: SwitchActiveReq; //Active status details

const { status, data } = await apiInstance.switchActiveJob(
    jobId,
    switchActiveReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **switchActiveReq** | **SwitchActiveReq**| Active status details | |
| **jobId** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

# **updateJob**
> JobRes updateJob(saveJobReq)

Modify an existing job

### Example

```typescript
import {
    JobApi,
    Configuration,
    SaveJobReq
} from './api';

const configuration = new Configuration();
const apiInstance = new JobApi(configuration);

let jobId: string; // (default to undefined)
let saveJobReq: SaveJobReq; //Updated job details

const { status, data } = await apiInstance.updateJob(
    jobId,
    saveJobReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **saveJobReq** | **SaveJobReq**| Updated job details | |
| **jobId** | [**string**] |  | defaults to undefined|


### Return type

**JobRes**

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

