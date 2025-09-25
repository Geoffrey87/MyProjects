package Memento.mapper;

import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserPreferenceOutputDto;
import Memento.dtos.InputDto.UserPreferenceUpdateDto;
import Memento.entities.Tag;
import Memento.entities.UserPreference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPreferenceMapper {

    public static void domainToDto(UserPreference preference, UserPreferenceOutputDto dto) {
        Objects.requireNonNull(preference, "preference must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(preference.getId());
        dto.setCreatedAt(preference.getCreatedAt());

        if (preference.getPreferredTags() != null) {
            List<TagOutputDto> tagDtos = preference.getPreferredTags().stream()
                    .map(tag -> {
                        TagOutputDto t = new TagOutputDto();
                        t.setId(tag.getId());
                        t.setName(tag.getName());
                        if (tag.getCategory() != null) {
                            t.setCategoryId(tag.getCategory().getId());
                        }
                        return t;
                    })
                    .collect(Collectors.toList());

            dto.setPreferredTags(tagDtos);
        }
    }


    public static void dtoToDomain(UserPreferenceUpdateDto dto, UserPreference preference, List<Tag> tags) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(preference, "preference must not be null");
        Objects.requireNonNull(tags, "tags must not be null");


        preference.setPreferredTags(tags.stream().collect(Collectors.toSet()));
    }
}
