package com.bqua.fleetops.job.adapter.outbound;

import com.bqua.fleetops.job.domain.event.JobEvent;
import com.bqua.fleetops.job.domain.outbound.JobEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringJobEventPublisher implements JobEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(JobEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
