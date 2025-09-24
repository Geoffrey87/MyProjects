package Memento.dtos.OutputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerOutputDto {
    private Long id;
    private LocalDate since;
    private UserSummaryDto follower;
    private UserSummaryDto following;
}

