package cs.youtrade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InnerKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String keyName;
    @Column(nullable = false)
    private String keyValue;

    public InnerKeyEntity(String keyName, String keyValue) {
        this.keyName = keyName;
        this.keyValue = keyValue;
    }
}
