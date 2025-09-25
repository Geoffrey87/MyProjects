package Memento.mapper;

import Memento.dtos.InputDto.UserSettingsUpdateDto;
import Memento.entities.UserSettings;

import java.util.Objects;

public final class UserSettingsMapper {

    private UserSettingsMapper() {
    }

    public static void applyUpdate(UserSettingsUpdateDto dto, UserSettings target) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(target, "target must not be null");

        if (dto.getAccountVisibility() != null) {
            target.setAccountVisibility(dto.getAccountVisibility());
        }
        if (dto.getCanReceiveRequests() != null) {
            target.setCanReceiveRequests(dto.getCanReceiveRequests());
        }
        if (dto.getDefaultPostVisibility() != null) {
            target.setDefaultPostVisibility(dto.getDefaultPostVisibility());
        }
        if (dto.getAllowFollowers() != null) {
            target.setAllowFollowers(dto.getAllowFollowers());
        }
        if (dto.getEmailNotifications() != null) {
            target.setEmailNotifications(dto.getEmailNotifications());
        }
        if (dto.getPushNotifications() != null) {
            target.setPushNotifications(dto.getPushNotifications());
        }
        if (dto.getIsDeactivated() != null) {
            target.setIsDeactivated(dto.getIsDeactivated());
        }
    }
}
