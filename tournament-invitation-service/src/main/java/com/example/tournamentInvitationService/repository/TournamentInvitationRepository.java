package com.example.tournamentInvitationService.repository;

import com.example.tournamentInvitationService.model.TournamentInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TournamentInvitationRepository extends JpaRepository<TournamentInvitation, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE TournamentInvitation t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.tournamentId = :tournamentId AND t.status = :status")
    void deleteByTournamentIdAndStatusEquals(Long tournamentId, String status);

    List<TournamentInvitation> findByTournamentIdAndUserIdAndStatus(Long tournamentId, Long userId, String pending);

    List<TournamentInvitation> findByUserIdAndStatusAndType(Long userId, String status,String type);
}