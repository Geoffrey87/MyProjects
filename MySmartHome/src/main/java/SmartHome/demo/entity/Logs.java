package SmartHome.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int logId;

    private String readingValue;

    private String timeStamp;

    private String unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "sensorId")
    private AbstractSensor sensor;


}
