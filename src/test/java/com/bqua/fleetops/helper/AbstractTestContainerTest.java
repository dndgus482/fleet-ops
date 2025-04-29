package com.bqua.fleetops.helper;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;


@Testcontainers
public abstract class AbstractTestContainerTest {

    static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:17.4")
                .withCopyFileToContainer(
                        MountableFile.forHostPath("./docker/init/init.sql"),
                        "/docker-entrypoint-initdb.d/init.sql"
                )
                .withInitScripts("insert.sql");
        POSTGRES_SQL_CONTAINER.start(); // 직접 스타트
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }

}