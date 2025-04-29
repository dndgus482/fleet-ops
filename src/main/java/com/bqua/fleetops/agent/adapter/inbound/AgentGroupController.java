package com.bqua.fleetops.agent.adapter.inbound;

import com.bqua.fleetops.agent.inbound.AgentGroupApi;
import com.bqua.fleetops.agent.inbound.dto.AgentConnectionRes;
import com.bqua.fleetops.agent.inbound.dto.AgentGroupRes;
import com.bqua.fleetops.agent.inbound.dto.AgentReq;
import com.bqua.fleetops.agent.inbound.dto.SaveAgentGroupReq;
import com.bqua.fleetops.agent.application.service.AgentGroupService;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AgentGroupController implements AgentGroupApi {

    private final AgentGroupService agentGroupService;

    public List<AgentGroupRes> getAllAgentGroup() {
        return agentGroupService.findAll();
    }

    public AgentGroupRes getAgentGroupById(String agentGroupId) {
        return agentGroupService.findById(agentGroupId);
    }

    public AgentGroupRes createAgentGroup(SaveAgentGroupReq req) {
        String agentGroupId = agentGroupService.create(req);
        return agentGroupService.findById(agentGroupId);
    }

    public AgentGroupRes updateAgentGroup(String agentGroupId, SaveAgentGroupReq req) {
        agentGroupService.update(agentGroupId, req);
        return agentGroupService.findById(agentGroupId);
    }

    public void deleteAgentGroupById(String agentGroupId) {
        agentGroupService.delete(agentGroupId);
    }

    public AgentGroupRes switchActiveAgentGroup(String agentGroupId, SwitchActiveReq switchActiveReq) {
        agentGroupService.switchActive(agentGroupId, switchActiveReq);
        return agentGroupService.findById(agentGroupId);
    }

    public List<SimpleJobNameRes> getAgentGroupLinkedJobs(String agentGroupId) {
        return agentGroupService.findAgentGroupLinkedJobs(agentGroupId);
    }

    public List<AgentConnectionRes> agentGroupConnectionTest(String agentGroupId) {
        return agentGroupService.agentGroupConnectionTest(agentGroupId);
    }

    public List<SimpleJobNameRes> getAgentLinkedJobs(AgentReq agentReq) {
        return agentGroupService.findAgentLinkedJobs(agentReq);
    }

    public List<AgentConnectionRes> agentConnectionTest(List<AgentReq> agentReqList) {
        return agentGroupService.agentConnectionTest(agentReqList);
    }

}
