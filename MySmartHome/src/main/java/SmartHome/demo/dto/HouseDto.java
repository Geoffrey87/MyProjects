package SmartHome.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.xml.transform.Source;

@Data
public class HouseDto {


    @Min(value = -90, message = "Latitude can't be less than -90")
    @Max(value = 90, message = "Latitude can't be more than 90" )
    private double latitude;

    @Min(value = -180, message = "Longitude can't be less than -180")
    @Max(value = 180, message = "Longitude can't be more than 180" )
    private double longitude;

    @NotBlank(message="Address must not be blank")
    @Size(min=5, message="Address must be at least 5 characters long")
    private String address;

    @NotBlank(message="Zip Code must not be blank")
    @Size(min=5, message="Zip Code must be at least 5 characters long")
    private String zipCode;

}
