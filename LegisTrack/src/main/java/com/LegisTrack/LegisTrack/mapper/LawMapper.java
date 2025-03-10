package com.LegisTrack.LegisTrack.mapper;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import com.LegisTrack.LegisTrack.entity.Law;
import org.springframework.stereotype.Component;

/**
 * This class is used to map the Law entity to the LawDto and vice versa
 */
@Component
public class LawMapper {

    public static LawDto domainToDto(Law law, LawDto lawDto){
        lawDto.setId(law.getId());
        lawDto.setDescription(law.getDescription());
        lawDto.setProposingPartyName(law.getProposingParty().getName());
        lawDto.setDateProposed(law.getDateProposed());
        return lawDto;
    }

    public static Law dtoToDomain(LawInputDto lawInputDto, Law law){
        law.setDescription(lawInputDto.getDescription());
        law.setDateProposed(lawInputDto.getDateProposed());
        return law;
    }
}
