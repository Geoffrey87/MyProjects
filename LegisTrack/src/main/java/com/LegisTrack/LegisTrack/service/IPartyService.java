package com.LegisTrack.LegisTrack.service;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;

import java.util.List;

public interface IParty {

    /**
     * Creates a new Party in the system.
     * @param partyInputDTO The data used to create the Party.
     * @return The created Party as a DTO.
     */
    PartyDto createParty(PartyInputDto partyInputDTO);

    /**
     * Increments the number of deputies in a Party.
     * @param partyId The ID of the Party.
     * @param count The number of deputies to add.
     * @return The updated Party as a DTO.
     */
    PartyDto incrementDeputies(Long partyId, int count);

    /**
     * Decrements the number of deputies in a Party.
     * @param partyId The ID of the Party.
     * @param count The number of deputies to remove.
     * @return The updated Party as a DTO.
     */
    public PartyDto decrementDeputies(Long partyId, int count);

    /**
     * Updates an existing Party.
     * @param id The ID of the Party to update.
     * @param partyInputDTO The new data for the Party.
     * @return The updated Party as a DTO.
     */
    PartyDto updateParty(Long id, PartyInputDto partyInputDTO);

    /**
     * Retrieves a Party by its ID.
     * @param id The ID of the Party.
     * @return The Party as a DTO.
     */
    PartyDto getPartyById(Long id);

    /**
     * Retrieves all Parties.
     * @return A list of PartyDTOs.
     */
    List<PartyDto> getAllParties();

    /**
     * Deletes a Party by its ID.
     * @param id The ID of the Party to delete.
     */
    void deleteParty(Long id);
}

