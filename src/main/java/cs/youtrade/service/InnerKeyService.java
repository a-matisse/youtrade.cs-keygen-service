package cs.youtrade.service;

import cs.youtrade.entity.InnerKeyEntity;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class InnerKeyService {
    private static final int CURRENT_BYTE_LENGTH = 32;
    private static final SecureRandom secureRandom = new SecureRandom();
    @Setter
    private static Set<String> keyNames = new HashSet<>();
    private final InnerKeyManagerService innerKeyManagerService;

    public InnerKeyService(
            @Autowired InnerKeyManagerService innerKeyManagerService
    ) {
        this.innerKeyManagerService = innerKeyManagerService;
    }

    @PostConstruct
    public void init() {
        keyNames.stream()
                .filter(keyName -> innerKeyManagerService.findByKeyName(keyName).isEmpty())
                .forEach(keyName -> {
                    InnerKeyEntity innerKeyEntity = new InnerKeyEntity(keyName, generateRandomKey());
                    innerKeyManagerService.save(innerKeyEntity);
                });
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES, initialDelay = 1)
    public void updateAllKeys() {
        innerKeyManagerService
                .findAll()
                .stream()
                .filter(key -> keyNames.contains(key.getKeyName()))
                .forEach(key -> {
                    String newKeyValue = generateRandomKey();
                    key.setKeyValue(newKeyValue);
                    innerKeyManagerService.save(key);
                });
    }

    private static String generateRandomKey() {
        byte[] randomBytes = new byte[InnerKeyService.CURRENT_BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
