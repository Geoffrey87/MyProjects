package Memento.dtos.OutputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileMusicOutputDto {
    private Long id;
    private String platform;
    private String trackUrl;
    private String title;
    private String artist;
    private LocalDateTime addedAt;
    private Boolean isActive;
}

