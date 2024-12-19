package SmartHome.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class House {

    /**
     * The unique identifier for the house.
     * GeneratedValue is used to automatically generate the HouseId.
     * GenericGenerator is used to generate the HouseId.
     * The strategy is set to "native" to allow the database to generate the HouseId.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int houseId;

    private String address;

    private String zipCode;

    private double latitude;

    private double longitude;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = Room.class)
    private Set<Room> rooms;


}
