package com.bqua.fleetops.infrastructure.ssh;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.Collections;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class KeyPairLoader {

    private final SshProperties sshProperties;

    @Getter
    private KeyPair keyPair;

    @PostConstruct
    public void init() {
        this.keyPair = loadFromFile();
    }

    private KeyPair loadFromFile() {
        Path keyPath = Paths.get(sshProperties.getPrivateKeyPath());

        FileKeyPairProvider provider = new FileKeyPairProvider();
        provider.setPaths(Collections.singletonList(keyPath));

        Iterable<KeyPair> keys = provider.loadKeys(null);
        Iterator<KeyPair> it = keys.iterator();
        if (!it.hasNext()) {
            throw new IllegalStateException("No private key found at: " + keyPath);
        }
        return it.next();
    }

    // TODO: user can edit config
}