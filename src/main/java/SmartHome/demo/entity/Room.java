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

    @Min(value = -3, message = "Floor can't be less than -3")
    @Max(value = 5, message = "Floor can't be more than 5")
    private byte floor;

    @Min(value = 0, message = "Length can't be negative")
    @Max(value = 100, message = "Length can't be greater than 100")
    private float length;

    @Min(value = 0, message = "Width can't be negative")
    @Max(value = 100, message = "Width can't be greater than 100")
    private float width;

    @Min(value = 0, message = "Height can't be negative")
    @Max(value = 100, message = "Height can't be greater than 100")
    private float height;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = Device.class)
    private Set<Device> devices;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "houseId")
    private House house;
}
