package Memento.dtos.InputDto;

import Memento.entities.Visibility;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsUpdateDto {

    private Visibility accountVisibility;

    @Pattern(regexp = "^(Everyone|FriendsOfFriends|Nobody)$",
            message = "canReceiveRequests must be one of: Everyone, FriendsOfFriends, Nobody")
    private String canReceiveRequests;

    private Visibility defaultPostVisibility;

    private Boolean allowFollowers;

    private Boolean emailNotifications;

    private Boolean pushNotifications;

    private Boolean isDeactivated;
}

