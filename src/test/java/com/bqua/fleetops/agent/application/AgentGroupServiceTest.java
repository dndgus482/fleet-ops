package com.bqua.fleetops.agent.application;

import com.bqua.fleetops.helper.AbstractTestContainerTest;
import com.bqua.fleetops.agent.application.service.AgentGroupService;
import com.bqua.fleetops.agent.inbound.dto.AgentGroupRes;
import com.bqua.fleetops.agent.inbound.dto.AgentReq;
import com.bqua.fleetops.agent.inbound.dto.SaveAgentGroupReq;
import com.bqua.fleetops.common.exception.BusinessException;
import com.bqua.fleetops.common.exception.NotFoundException;
import com.bqua.fleetops.job.inbound.dto.SimpleJobNameRes;
import com.bqua.fleetops.job.inbound.dto.SwitchActiveReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class AgentGroupServiceTest extends AbstractTestContainerTest {

    @Autowired
    private AgentGroupService agentGroupService;

    @Test
    void shouldReturnAllAgentGroups() {
        // Given & When
        List<AgentGroupRes> agentGroups = agentGroupService.findAll();

        // Then
        Assertions.assertEquals(2, agentGroups.size());
    }

    @Test
    void shouldCreateAndRetrieveAgentGroup() {
        // Given
        SaveAgentGroupReq req = new SaveAgentGroupReq(
                "name",
                "description",
                List.of(
                        new AgentReq("127.0.0.1", "user1"),
                        new AgentReq("127.0.0.1", "user2")
                ),
                List.of("tag1", "tag2"),
                true
        );

        // When
        String savedId = agentGroupService.create(req);
        AgentGroupRes agentGroupRes = agentGroupService.findById(savedId);

        // Then
        Assertions.assertNotNull(agentGroupRes);
        Assertions.assertNotNull(agentGroupRes.getAgentGroupId());
        Assertions.assertEquals("name", agentGroupRes.getAgentGroupName());
        Assertions.assertEquals("description", agentGroupRes.getAgentGroupDescription());
        Assertions.assertTrue(agentGroupRes.getActive());

        Assertions.assertEquals("127.0.0.1", agentGroupRes.getAgents().getFirst().getIp());
        Assertions.assertEquals("user1", agentGroupRes.getAgents().getFirst().getUserName());
        Assertions.assertEquals("user2", agentGroupRes.getAgents().get(1).getUserName());

        Assertions.assertEquals("tag1", agentGroupRes.getTags().getFirst());
        Assertions.assertEquals("tag2", agentGroupRes.getTags().get(1));
    }

    @Test
    void shouldUpdateAgentGroupSuccessfully() {
        // Given
        String groupId = "1";
        SaveAgentGroupReq updateReq = new SaveAgentGroupReq(
                "updatedName",
                "updatedDescription",
                List.of(
                        new AgentReq("192.168.1.2", "user2"),
                        new AgentReq("192.168.1.3", "user3")
                ),
                List.of("updatedTag1", "updatedTag2"),
                false
        );

        // When
        agentGroupService.update(groupId, updateReq);
        AgentGroupRes updatedGroup = agentGroupService.findById(groupId);

        // Then
        Assertions.assertNotNull(updatedGroup);
        Assertions.assertEquals("updatedName", updatedGroup.getAgentGroupName());
        Assertions.assertEquals("updatedDescription", updatedGroup.getAgentGroupDescription());
        Assertions.assertFalse(updatedGroup.getActive());
        Assertions.assertEquals(2, updatedGroup.getAgents().size());
        Assertions.assertEquals("192.168.1.2", updatedGroup.getAgents().get(0).getIp());
        Assertions.assertEquals("user2", updatedGroup.getAgents().get(0).getUserName());
        Assertions.assertEquals("192.168.1.3", updatedGroup.getAgents().get(1).getIp());
        Assertions.assertEquals("user3", updatedGroup.getAgents().get(1).getUserName());
        Assertions.assertEquals(2, updatedGroup.getTags().size());
        Assertions.assertEquals("updatedTag1", updatedGroup.getTags().get(0));
        Assertions.assertEquals("updatedTag2", updatedGroup.getTags().get(1));
    }

    @Test
    void shouldThrowExceptionWhenDeletingAgentGroupWithLinkedJobs() {
        String groupId = "1";
        Assertions.assertNotNull(agentGroupService.findById(groupId));

        Assertions.assertThrows(BusinessException.class, () -> agentGroupService.delete(groupId));
    }

    @Test
    void shouldDelete() {
        // Given
        String groupId = "2";
        Assertions.assertNotNull(agentGroupService.findById(groupId));

        // When
        agentGroupService.delete(groupId);

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> agentGroupService.findById(groupId));
    }


    @Test
    void shouldSwitchAgentGroupActiveState() {
        // Given
        String groupId = "1";
        AgentGroupRes originalGroup = agentGroupService.findById(groupId);
        Assertions.assertNotNull(originalGroup);
        boolean initialActiveState = originalGroup.getActive();

        // When
        agentGroupService.switchActive(groupId, new SwitchActiveReq(!initialActiveState));
        AgentGroupRes updatedGroup = agentGroupService.findById(groupId);

        // Then
        Assertions.assertEquals(!initialActiveState, updatedGroup.getActive());
    }

    @Test
    void shouldReturnLinkedJobsForAgentGroup() {
        // Given
        String groupId = "1";

        // When
        List<SimpleJobNameRes> linkedJobs = agentGroupService.findAgentGroupLinkedJobs(groupId);

        // Then
        Assertions.assertNotNull(linkedJobs);
        Assertions.assertFalse(linkedJobs.isEmpty());
    }
}