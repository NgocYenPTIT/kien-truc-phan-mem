package com.example.tournamentPlayerService.service;

import com.example.tournamentPlayerService.DTOs.TournamentPlayerDto;
import com.example.tournamentPlayerService.DTOs.UserDto;
import com.example.tournamentPlayerService.model.TournamentPlayer;
import com.example.tournamentPlayerService.model.TournamentPlayerRequest;
import com.example.tournamentPlayerService.repository.TournamentPlayerRepository;
import com.example.tournamentPlayerService.util.ServiceAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class TournamentPlayerService {
    @Autowired
    private TournamentPlayerRepository tournamentPlayerRepository;
    @Autowired
    private ServiceAPI serviceAPI;

    @Value("${app.global.url.user-service}")
    private String urlUserService;

    public ResponseEntity<?> getListPlayers(Long id, String username, Long currentPage, Long pageSize) {
        // Thiết lập phân trang và sắp xếp theo playerName
        Pageable pageable = PageRequest.of(
                currentPage.intValue() - 1, // currentPage bắt đầu từ 1, nhưng PageRequest dùng index từ 0
                pageSize.intValue(),
                Sort.by("playerName").ascending() // Sắp xếp theo playerName
        );

        Page<TournamentPlayer> playerPage;

        // Kiểm tra nếu username không null và không rỗng
        if (username != null && !username.trim().isEmpty()) {
            playerPage = tournamentPlayerRepository.findByTournamentIdAndPlayerNameContainingIgnoreCaseAndDeletedAtIsNull(
                    id, username, pageable);
        } else {
            playerPage = tournamentPlayerRepository.findByTournamentIdAndDeletedAtIsNull(id, pageable);
        }

        // Chuyển đổi danh sách TournamentPlayer sang TournamentPlayerDto
        List<TournamentPlayerDto> playerDtos = playerPage.getContent().stream()
                .map(player -> new TournamentPlayerDto(
                        player.getId(),
                        player.getTournamentId(),
                        player.getPlayerId(),
                        player.getPlayerName(), // Ánh xạ playerName
                        player.getStatus(),
                        player.getCreatedAt(),
                        player.getUpdatedAt(),
                        player.getDeletedAt()
                ))
                .collect(Collectors.toList());

        // Tạo response với thông tin phân trang
        Map<String, Object> response = new HashMap<>();
        response.put("players", playerDtos);
        response.put("currentPage", playerPage.getNumber() + 1);
        response.put("totalPages", playerPage.getTotalPages());
        response.put("totalItems", playerPage.getTotalElements());
        response.put("pageSize", pageSize);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addPlayer(HttpServletRequest request, TournamentPlayerRequest player) {
        Long playerId = (Long) player.getPlayerId();
        Long tournamentId = (Long) player.getTournamentId();

        UserDto user = this.serviceAPI.call(
                this.urlUserService + "user/" + playerId,
                HttpMethod.GET,
                null,
                UserDto.class,
                (String) request.getAttribute("token")
        );

        TournamentPlayer tournamentPlayer = TournamentPlayer.builder()
                .playerId(playerId)
                .tournamentId(tournamentId)
                .playerName(user.getUsername())
                .build();

        tournamentPlayerRepository.save(tournamentPlayer);

        return ResponseEntity.ok(TournamentPlayerDto.builder().playerId(playerId).tournamentId(tournamentId).playerName(user.getUsername()).build());
    }

    @Transactional
    public ResponseEntity<?> delete(HttpServletRequest request,  TournamentPlayerRequest player) {
        Long playerId = (Long) player.getPlayerId();
        Long tournamentId = (Long) player.getTournamentId();
        System.out.println(playerId);
        this.tournamentPlayerRepository.deleteByTournamentIdAndPlayerId(tournamentId, playerId);

        return ResponseEntity.ok(TournamentPlayerDto.builder().playerId(playerId).tournamentId(tournamentId).build());
    }

}