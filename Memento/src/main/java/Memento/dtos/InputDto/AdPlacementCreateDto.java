package Memento.dtos.InputDto;

import Memento.entities.Visibility;
import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdPlacementCreateDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Target URL is required")
    @Pattern(regexp = "^(https?://).+", message = "Target URL must be valid")
    private String targetUrl;

    @NotBlank(message = "Media URL is required")
    private String mediaUrl;

    @NotNull(message = "StartAt is required")
    private LocalDateTime startAt;

    @NotNull(message = "EndAt is required")
    private LocalDateTime endAt;

    @NotBlank(message = "Advertiser name is required")
    private String advertiserName;

    private Long categoryId; // optional
    private Long tagId;      // optional
}

