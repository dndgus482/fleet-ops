package com.bqua.fleetops.job.domain.outbound;

import com.bqua.fleetops.job.domain.event.JobEvent;

public interface JobEventPublisher {

    void publish(JobEvent event);

}
