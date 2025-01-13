package SmartHome.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractActuator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int actuatorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "deviceId", nullable = true)
    private Device device;
}
