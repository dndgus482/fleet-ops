# AgentGroupApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**agentConnectionTest**](#agentconnectiontest) | **POST** /api/agent/connectionTest | Test connection for given agents|
|[**agentGroupConnectionTest**](#agentgroupconnectiontest) | **POST** /api/agentGroups/{agentGroupId}/connectionTest | Test connection for all agents in a group|
|[**createAgentGroup**](#createagentgroup) | **POST** /api/agentGroups | Create a new agent group|
|[**deleteAgentGroupById**](#deleteagentgroupbyid) | **DELETE** /api/agentGroups/{agentGroupId} | Delete an agent group by ID|
|[**getAgentGroupById**](#getagentgroupbyid) | **GET** /api/agentGroups/{agentGroupId} | Get agent group by ID|
|[**getAgentGroupLinkedJobs**](#getagentgrouplinkedjobs) | **GET** /api/agentGroups/{agentGroupId}/linkedJobs | Get jobs linked to a specific agent group|
|[**getAgentLinkedJobs**](#getagentlinkedjobs) | **GET** /api/agent/linkedJobs | Get jobs linked to a specific agent|
|[**getAllAgentGroup**](#getallagentgroup) | **GET** /api/agentGroups | Get all agent groups|
|[**switchActiveAgentGroup**](#switchactiveagentgroup) | **POST** /api/agentGroups/{agentGroupId}/switchActive | Toggle active state of an agent group|
|[**updateAgentGroup**](#updateagentgroup) | **PUT** /api/agentGroups/{agentGroupId} | Update an existing agent group|

# **agentConnectionTest**
> Array<AgentConnectionRes> agentConnectionTest()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentReqList: Array<AgentReq>; // (default to undefined)

const { status, data } = await apiInstance.agentConnectionTest(
    agentReqList
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentReqList** | **Array&lt;AgentReq&gt;** |  | defaults to undefined|


### Return type

**Array<AgentConnectionRes>**

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

# **agentGroupConnectionTest**
> Array<AgentConnectionRes> agentGroupConnectionTest()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)

const { status, data } = await apiInstance.agentGroupConnectionTest(
    agentGroupId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentGroupId** | [**string**] |  | defaults to undefined|


### Return type

**Array<AgentConnectionRes>**

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

# **createAgentGroup**
> AgentGroupRes createAgentGroup(saveAgentGroupReq)


### Example

```typescript
import {
    AgentGroupApi,
    Configuration,
    SaveAgentGroupReq
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let saveAgentGroupReq: SaveAgentGroupReq; //

const { status, data } = await apiInstance.createAgentGroup(
    saveAgentGroupReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **saveAgentGroupReq** | **SaveAgentGroupReq**|  | |


### Return type

**AgentGroupRes**

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

# **deleteAgentGroupById**
> deleteAgentGroupById()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)

const { status, data } = await apiInstance.deleteAgentGroupById(
    agentGroupId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentGroupId** | [**string**] |  | defaults to undefined|


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

# **getAgentGroupById**
> AgentGroupRes getAgentGroupById()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)

const { status, data } = await apiInstance.getAgentGroupById(
    agentGroupId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentGroupId** | [**string**] |  | defaults to undefined|


### Return type

**AgentGroupRes**

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

# **getAgentGroupLinkedJobs**
> Array<SimpleJobNameRes> getAgentGroupLinkedJobs()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)

const { status, data } = await apiInstance.getAgentGroupLinkedJobs(
    agentGroupId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentGroupId** | [**string**] |  | defaults to undefined|


### Return type

**Array<SimpleJobNameRes>**

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

# **getAgentLinkedJobs**
> Array<SimpleJobNameRes> getAgentLinkedJobs()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration,
    AgentReq
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentReq: AgentReq; // (default to undefined)

const { status, data } = await apiInstance.getAgentLinkedJobs(
    agentReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **agentReq** | **AgentReq** |  | defaults to undefined|


### Return type

**Array<SimpleJobNameRes>**

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

# **getAllAgentGroup**
> Array<AgentGroupRes> getAllAgentGroup()


### Example

```typescript
import {
    AgentGroupApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

const { status, data } = await apiInstance.getAllAgentGroup();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<AgentGroupRes>**

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

# **switchActiveAgentGroup**
> AgentGroupRes switchActiveAgentGroup(switchActiveReq)


### Example

```typescript
import {
    AgentGroupApi,
    Configuration,
    SwitchActiveReq
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)
let switchActiveReq: SwitchActiveReq; //

const { status, data } = await apiInstance.switchActiveAgentGroup(
    agentGroupId,
    switchActiveReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **switchActiveReq** | **SwitchActiveReq**|  | |
| **agentGroupId** | [**string**] |  | defaults to undefined|


### Return type

**AgentGroupRes**

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

# **updateAgentGroup**
> AgentGroupRes updateAgentGroup(saveAgentGroupReq)


### Example

```typescript
import {
    AgentGroupApi,
    Configuration,
    SaveAgentGroupReq
} from './api';

const configuration = new Configuration();
const apiInstance = new AgentGroupApi(configuration);

let agentGroupId: string; // (default to undefined)
let saveAgentGroupReq: SaveAgentGroupReq; //

const { status, data } = await apiInstance.updateAgentGroup(
    agentGroupId,
    saveAgentGroupReq
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **saveAgentGroupReq** | **SaveAgentGroupReq**|  | |
| **agentGroupId** | [**string**] |  | defaults to undefined|


### Return type

**AgentGroupRes**

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

