package SmartHome.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int roomId;

    private String roomName;

    private byte floor;

    private float length;

    private float width;

    private float height;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = Device.class)
    private Set<Device> devices;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "houseId")
    private House house;
}
