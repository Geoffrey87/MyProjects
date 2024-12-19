package SmartHome.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HouseDto {

    @NotBlank(message="Latitude must not be blank")
    @Size(min=5, message="Latitude must be at least 5 characters long")
    private double latitude;

    @NotBlank(message="Longitude must not be blank")
    @Size(min=5, message="Longitude must be at least 5 characters long")
    private double longitude;

    @NotBlank(message="Address must not be blank")
    @Size(min=5, message="Address must be at least 5 characters long")
    private String address;

    @NotBlank(message="Zip Code must not be blank")
    @Size(min=5, message="Zip Code must be at least 5 characters long")
    private String zipCode;






}
