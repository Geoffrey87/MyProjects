package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.entity.*;
import com.LegisTrack.LegisTrack.exception.BusinessException;
import com.LegisTrack.LegisTrack.mapper.VoteMapper;
import com.LegisTrack.LegisTrack.repository.VoteRepo;
import com.LegisTrack.LegisTrack.repository.PartyRepo;
import com.LegisTrack.LegisTrack.repository.LawRepo;
import com.LegisTrack.LegisTrack.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteService implements IVoteService {


    private final VoteRepo voteRepo;
    private final PartyRepo partyRepo;
    private final LawRepo lawRepo;

    @Autowired
    public VoteService(VoteRepo voteRepo, PartyRepo partyRepo, LawRepo lawRepo) {
        this.voteRepo = voteRepo;
        this.partyRepo = partyRepo;
        this.lawRepo = lawRepo;
    }

    @Override
    public List<VoteDto> generateVotes(Long lawId, List<Long> partyIds) {
        Law law = lawRepo.findById(lawId)
                .orElseThrow(() -> new BusinessException("Law not found"));

        // 1) Get the proposing party
        Party proposingParty = law.getProposingParty();


        // 2) If the proposing party is not in the list, add it
        if (proposingParty != null
                && !voteRepo.existsByLawIdAndPartyId(lawId, proposingParty.getId())) {
            Vote forcedVote = new Vote();
            forcedVote.setLaw(law);
            forcedVote.setParty(proposingParty);
            forcedVote.setVoteType(VoteType.IN_FAVOR);
            voteRepo.save(forcedVote);
        }

        // 3) Removes the proposing party from the list
        List<Long> filteredPartyIds = partyIds.stream()
                .filter(id -> !id.equals(proposingParty.getId()))
                .collect(Collectors.toList());

        // 4) Get selected parties
        List<Party> selectedParties = partyRepo.findAllById(filteredPartyIds);

        // 5) Filtering parties that already voted
        List<Party> availableParties = selectedParties.stream()
                .filter(p -> !voteRepo.existsByLawIdAndPartyId(lawId, p.getId()))
                .collect(Collectors.toList());

        if (availableParties.isEmpty()) {
            throw new BusinessException("All parties already voted or no parties selected");
        }

        // 6) Shuffle and distribute votes
        Collections.shuffle(availableParties);
        int total = availableParties.size();

        int favor = (int) Math.round(total * 0.40);
        int against = (int) Math.round(total * 0.35);
        int abstention = total - favor - against;

        List<Vote> votes = new ArrayList<>();
        int index = 0;

        // FAV
        while (index < favor) {
            votes.add(createVote(law, availableParties.get(index), VoteType.IN_FAVOR));
            index++;
        }
        // AGAINST
        while (index < favor + against) {
            votes.add(createVote(law, availableParties.get(index), VoteType.AGAINST));
            index++;
        }
        // ABSTENTION
        while (index < total) {
            votes.add(createVote(law, availableParties.get(index), VoteType.ABSTENTION));
            index++;
        }

        voteRepo.saveAll(votes);


        List<VoteDto> result = votes.stream()
                .map(v -> VoteMapper.domainToDto(v, new VoteDto()))
                .collect(Collectors.toList());


        return result;
    }

    private Vote createVote(Law law, Party party, VoteType type) {
        Vote vote = new Vote();
        vote.setLaw(law);
        vote.setParty(party);
        vote.setVoteType(type);
        return vote;
    }


    @Override
    public Map<VoteType, List<String>> getVotesByLaw(Long lawId) {
        return voteRepo.findByLawId(lawId).stream()
                .collect(Collectors.groupingBy(
                        Vote::getVoteType,
                        () -> new EnumMap<>(VoteType.class),
                        Collectors.mapping(v -> v.getParty().getName(), Collectors.toList())
                ));
    }

}