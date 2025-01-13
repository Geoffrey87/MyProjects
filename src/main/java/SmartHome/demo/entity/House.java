package SmartHome.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(value = -90, message = "Latitude can't be less than -90")
    @Max(value = 90, message = "Latitude can't be more than 90" )
    private double latitude;

    @Min(value = -180, message = "Longitude can't be less than -180")
    @Max(value = 180, message = "Longitude can't be more than 180" )
    private double longitude;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,targetEntity = Room.class)
    private Set<Room> rooms;

}
