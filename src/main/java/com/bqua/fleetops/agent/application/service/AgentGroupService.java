package com.bqua.fleetops.agent.application.service;

import com.bqua.fleetops.agent.domain.logic.AgentGroupValidator;
import com.bqua.fleetops.agent.domain.outbound.SshAgentCommandsPort;
import com.bqua.fleetops.agent.inbound.dto.AgentConnectionRes;
import com.bqua.fleetops.agent.inbound.dto.AgentGroupRes;
import com.bqua.fleetops.agent.inbound.dto.AgentReq;
import com.bqua.fleetops.agent.inbound.dto.SaveAgentGroupReq;
import com.bqua.fleetops.agent.domain.entity.Agent;
import com.bqua.fleetops.agent.domain.entity.AgentGroup;
import com.bqua.fleetops.agent.domain.outbound.AgentGroupRepository;
import com.bqua.fleetops.common.exception.BusinessException;
import com.bqua.fleetops.job.inbound.JobQueryAccessor;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import com.bqua.fleetops.agent.application.mapper.AgentGroupDtoMapper;
import com.bqua.fleetops.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentGroupService {

    private final AgentGroupRepository agentGroupRepository;
    private final JobQueryAccessor jobQueryAccessor;
    private final SshAgentCommandsPort sshAgentCommandsPort;
    private final AgentGroupValidator agentGroupValidator;

    public List<AgentGroupRes> findAll() {
        return agentGroupRepository.findAll()
                .stream()
                .map(AgentGroupDtoMapper.INSTANCE::toRes)
                .collect(Collectors.toList());
    }

    public AgentGroupRes findById(String agentGroupId) {
        return agentGroupRepository.findById(agentGroupId)
                .map(AgentGroupDtoMapper.INSTANCE::toRes)
                .orElseThrow(() -> new NotFoundException(agentGroupId));
    }

    @Transactional
    public String create(SaveAgentGroupReq req) {
        AgentGroup agentGroup = AgentGroupDtoMapper.INSTANCE.saveAgentGroupReq(req);
        agentGroup.generateId();
        // TODO: generate 한 ID 가 충돌되지 않는다고 판단.
        agentGroup.validate(agentGroupValidator);

        AgentGroup saved = agentGroupRepository.create(agentGroup);
        return saved.getAgentGroupId();
    }

    @Transactional
    public String update(String agentGroupId, SaveAgentGroupReq req) {
        agentGroupRepository.findById(agentGroupId)
                .orElseThrow(() -> new NotFoundException(agentGroupId));

        AgentGroup agentGroup = AgentGroupDtoMapper.INSTANCE.saveAgentGroupReq(req);
        agentGroup.setAgentGroupId(agentGroupId);
        agentGroup.validate(agentGroupValidator);

        AgentGroup updated = agentGroupRepository.update(agentGroup);
        return updated.getAgentGroupId();
    }

    @Transactional
    public void delete(String agentGroupId) {
        agentGroupRepository.findById(agentGroupId)
                .orElseThrow(() -> new NotFoundException(agentGroupId));

        List<SimpleJobNameRes> linkedJobs = jobQueryAccessor.findAgentGroupLinkedJobs(agentGroupId);
        if (!CollectionUtils.isEmpty(linkedJobs)) {
            throw new BusinessException("Cannot delete agent group that has linked jobs. Please unlink all jobs before deletion.");
        }

        agentGroupRepository.delete(agentGroupId);
    }

    @Transactional
    public void switchActive(String agentGroupId, SwitchActiveReq switchActiveReq) {
        AgentGroup agentGroup = agentGroupRepository.findById(agentGroupId)
                .orElseThrow(() -> new NotFoundException(agentGroupId));

        // TODO: disable 된 그룹은 잡 실행 안되게
        agentGroup.setActive(switchActiveReq.getActive());
        agentGroupRepository.update(agentGroup);
    }

    public List<SimpleJobNameRes> findAgentGroupLinkedJobs(String agentGroupId) {
        agentGroupRepository.findById(agentGroupId)
                .orElseThrow(() -> new NotFoundException(agentGroupId));

        return jobQueryAccessor.findAgentGroupLinkedJobs(agentGroupId);
    }

    public List<SimpleJobNameRes> findAgentLinkedJobs(AgentReq agentReq) {
        return jobQueryAccessor.findAgentLinkedJobs(agentReq.getIp(), agentReq.getUserName());
    }

    public List<AgentConnectionRes> agentGroupConnectionTest(String agentGroupId) {
        AgentGroup agentGroup = agentGroupRepository.findById(agentGroupId)
                .orElseThrow(() -> new NotFoundException(agentGroupId));

        List<Agent> agents = agentGroup.getAgents();
        return agents.stream()
                .map(req -> sshAgentCommandsPort.connectionTest(req.getIpString(), req.getUserName()))
                .toList();
    }

    public List<AgentConnectionRes> agentConnectionTest(List<AgentReq> agentReqList) {
        return agentReqList.stream()
                .map(req -> sshAgentCommandsPort.connectionTest(req.getIp(), req.getUserName()))
                .toList();
    }

}
