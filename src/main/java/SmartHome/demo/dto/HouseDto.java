package SmartHome.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.xml.transform.Source;

@Data
public class HouseDto {



    private double latitude;


    private double longitude;

    @NotBlank(message="Address must not be blank")
    @Size(min=5, message="Address must be at least 5 characters long")
    private String address;

    @NotBlank(message="Zip Code must not be blank")
    @Size(min=5, message="Zip Code must be at least 5 characters long")
    private String zipCode;

}
