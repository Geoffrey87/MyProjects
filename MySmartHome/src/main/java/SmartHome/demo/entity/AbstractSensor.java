package SmartHome.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class AbstractSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int sensorId;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,targetEntity = Logs.class)
    @JoinColumn(name = "logId", nullable = true)
    private Set<Logs> logs;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "deviceId", nullable = true)
    private Device device;




}
