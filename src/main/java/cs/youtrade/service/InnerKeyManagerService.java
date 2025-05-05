package cs.youtrade.service;

import cs.youtrade.entity.InnerKeyEntity;
import cs.youtrade.repository.InnerKeyRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InnerKeyManagerService {
    private final InnerKeyRepository innerKeyRepository;

    public InnerKeyManagerService(InnerKeyRepository innerKeyRepository) {
        this.innerKeyRepository = innerKeyRepository;
    }

    @Transactional
    @Modifying
    public void save(InnerKeyEntity innerKeyEntity) {
        innerKeyRepository.save(innerKeyEntity);
    }

    public Optional<InnerKeyEntity> findByKeyName(String keyName) {
        return innerKeyRepository.findByKeyName(keyName);
    }

    public List<InnerKeyEntity> findAll() {
        return innerKeyRepository.findAll();
    }
}
