insert into agent_groups (agent_group_id, agent_group_name, agent_group_description, active, reg_user_id, reg_date_time,
                          mod_user_id, mod_date_time)
values ('1', 'group1', 'description', true, 'SYSTEM', '2025-04-06 00:00:00', 'SYSTEM', '2025-04-06 01:00:00'),
       ('2', 'group2', 'description2', false, 'SYSTEM', '2025-04-06 00:00:00', 'SYSTEM', '2025-04-06 01:00:00');

insert into agent_groups_agents (agent_group_id, order_idx, ip, user_name)
values ('1', 0, '127.0.0.1', 'user1'),
       ('1', 1, '127.0.0.1', 'user2'),
       ('2', 0, '127.0.0.2', 'user1');

insert into agent_groups_tags (agent_group_id, tag_value)
values ('1', 'DEV'),
       ('1', 'TEST');

insert into jobs (job_id, job_name, job_description, job_type, active, period, script)
values ('1', 'jobName1', 'jobDescription1', 'SSH', true, '* * * * *', 'echo hello'),
       ('2', 'jobName2', 'jobDescription2', 'SSH', true, '1 * * * *', 'echo hello');

insert into jobs_target_agents (job_id, order_idx, target_agent_type, agent_group_id, ip, user_name)
values ('1', 0, 'GROUP', '1', null, null),
       ('1', 1, 'AGENT', null, '192.168.0.1', 'user1'),
       ('2', 0, 'AGENT', null, '192.168.0.2', 'user1');

insert into job_executions (job_execution_id, job_id, job_execution_no, job_history_no,
                            job_execution_status, execution_user_id, execution_date_time, start_date_time,
                            end_date_time)
values ('1', '1', 1, 1, 'COMPLETED', 'SYSTEM', '2025-04-06 01:00:00', '2025-04-06 02:00:00', '2025-04-06 03:00:00'),
       ('2', '1', 2, 1, 'COMPLETED', 'SYSTEM', '2025-04-07 01:00:00', '2025-04-07 02:00:00', '2025-04-07 03:00:00'),
       ('3', '1', 3, 2, 'STOPPED', 'SYSTEM', '2025-04-08 01:00:00', '2025-04-08 02:00:00', '2025-04-08 03:00:00');

insert into job_executions_target_agents (job_execution_id, order_idx, ip, user_name, agent_group_id,
                                          job_execution_target_agent_status, start_date_time, end_date_time, log)
values ('1', 0, '127.0.0.1', 'user1', null, 'COMPLETED', '2025-04-06 01:00:00', '2025-04-06 02:00:00', null),
       ('1', 1, '127.0.0.1', 'user2', null, 'COMPLETED', '2025-04-06 01:00:00', '2025-04-06 02:00:00', null),
       ('1', 2, '192.168.0.1', 'user1', null, 'COMPLETED', '2025-04-06 01:00:00', '2025-04-06 02:00:00', null);

insert into job_history (job_id, job_history_no, job_name, job_type, history, reg_user_id, reg_date_time)
values ('1', 1, 'jobNameXXX', 'SSH', '{}', 'SYSTEM', '2025-04-06 01:00:00'),
       ('1', 2, 'jobName1', 'SSH', '{}', 'SYSTEM', '2025-04-06 01:00:00');
