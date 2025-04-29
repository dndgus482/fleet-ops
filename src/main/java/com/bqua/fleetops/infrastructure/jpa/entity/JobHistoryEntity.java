package com.bqua.fleetops.infrastructure.jpa.entity;

import com.bqua.fleetops.job.domain.entity.job.enums.JobType;
import com.bqua.fleetops.infrastructure.jpa.converter.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.Map;

@Entity
@Table(name = "job_history", uniqueConstraints = {@UniqueConstraint(columnNames = {"job_id", "job_history_no"})})
@Getter @Setter
public class JobHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "job_id", nullable = false, updatable = false)
    private String jobId;

    @Column(name = "job_history_no", nullable = false, updatable = false)
    private Long jobHistoryNo;

    @Column(name = "job_name", nullable = false, updatable = false)
    private String jobName;

    @Column(name = "job_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Convert(converter = MapToJsonConverter.class)
    @Column(name = "history", columnDefinition = "TEXT", updatable = false)
    private Map<String, Object> history;

    @Column(name = "reg_user_id", updatable = false)
    private String regUserId;

    @CreationTimestamp
    @Column(name = "reg_date_time", nullable = false, updatable = false)
    private ZonedDateTime regDateTime;

}