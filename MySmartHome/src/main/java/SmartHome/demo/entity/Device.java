package SmartHome.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int deviceId;

    private String deviceName;

    private String deviceType;

    private boolean status;

    /**
     * Many to one because many devices can be in one room.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "roomId", nullable = true)
    private Room room;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = AbstractSensor.class)
    @JoinColumn(name = "sensorId", nullable = true)
    private Set<AbstractSensor> sensors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = AbstractSensor.class)
    @JoinColumn(name = "sensorId", nullable = true)
    private Set<AbstractActuator> actuators = new HashSet<>();


}
