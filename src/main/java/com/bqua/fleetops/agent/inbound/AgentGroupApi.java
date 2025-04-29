package com.bqua.fleetops.agent.inbound;

import com.bqua.fleetops.agent.inbound.dto.AgentConnectionRes;
import com.bqua.fleetops.agent.inbound.dto.AgentGroupRes;
import com.bqua.fleetops.agent.inbound.dto.AgentReq;
import com.bqua.fleetops.agent.inbound.dto.SaveAgentGroupReq;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@Tag(name = "AgentGroup", description = "API for managing agent groups and their connections")
public interface AgentGroupApi {

    @Operation(summary = "Get all agent groups")
    @GetMapping("/agentGroups")
    List<AgentGroupRes> getAllAgentGroup();

    @Operation(summary = "Get agent group by ID")
    @GetMapping("/agentGroups/{agentGroupId}")
    AgentGroupRes getAgentGroupById(@PathVariable String agentGroupId);

    @Operation(summary = "Create a new agent group")
    @PostMapping("/agentGroups")
    AgentGroupRes createAgentGroup(@RequestBody @Valid SaveAgentGroupReq req);

    @Operation(summary = "Update an existing agent group")
    @PutMapping("/agentGroups/{agentGroupId}")
    AgentGroupRes updateAgentGroup(
            @PathVariable @Valid @NotBlank(message = "agentGroupId must be not be blank") String agentGroupId,
            @RequestBody @Valid SaveAgentGroupReq req
    );

    @Operation(summary = "Delete an agent group by ID")
    @DeleteMapping("/agentGroups/{agentGroupId}")
    void deleteAgentGroupById(@PathVariable String agentGroupId);

    @Operation(summary = "Toggle active state of an agent group")
    @PostMapping("/agentGroups/{agentGroupId}/switchActive")
    AgentGroupRes switchActiveAgentGroup(@PathVariable String agentGroupId, @RequestBody SwitchActiveReq switchActiveReq);

    @Operation(summary = "Get jobs linked to a specific agent group")
    @GetMapping("/agentGroups/{agentGroupId}/linkedJobs")
    List<SimpleJobNameRes> getAgentGroupLinkedJobs(@PathVariable String agentGroupId);

    @Operation(summary = "Test connection for all agents in a group")
    @PostMapping("/agentGroups/{agentGroupId}/connectionTest")
    List<AgentConnectionRes> agentGroupConnectionTest(@PathVariable String agentGroupId);

    @Operation(summary = "Get jobs linked to a specific agent")
    @GetMapping("/agent/linkedJobs")
    List<SimpleJobNameRes> getAgentLinkedJobs(AgentReq agentReq);

    @Operation(summary = "Test connection for given agents")
    @PostMapping("/agent/connectionTest")
    List<AgentConnectionRes> agentConnectionTest(List<AgentReq> agentReqList);

}
