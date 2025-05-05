package com.bqua.fleetops.infrastructure.ssh;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fleet-ops.ssh")
@Setter
@Getter
@NoArgsConstructor
public class SshProperties {
    private String privateKeyPath;
}