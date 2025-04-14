package com.example.tournamentservice.config;

import com.example.tournamentservice.model.Tournament;
import com.example.tournamentservice.repository.TournamentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class TournamentDataInitializer {

    @Bean
    public CommandLineRunner initTournamentData(TournamentRepository tournamentRepository) {
        return args -> {
            // Kiểm tra xem đã có dữ liệu chưa
            if (tournamentRepository.count() == 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();

                // Tournament 1 - Giải đấu cờ vua quốc tế
                Tournament internationalTournament = new Tournament();
                internationalTournament.setName("International Chess Championship 2025");
                internationalTournament.setDescription("Giải đấu cờ vua quốc tế với sự tham gia của các kỳ thủ hàng đầu");
                internationalTournament.setOrganizerId(1L);  // Admin là người tổ chức
                internationalTournament.setBoardTypeId(1L);  // Bàn cờ vua chuẩn
                internationalTournament.setOrganizingMethodId(1L);  // Phương thức Swiss
                internationalTournament.setMaxPlayer(64);
                internationalTournament.setStatus("Open");
                internationalTournament.setStartDate(dateFormat.parse("2025-06-01"));
                internationalTournament.setEndDate(dateFormat.parse("2025-06-15"));

                // Tournament 2 - Giải đấu cờ vua nhanh
                Tournament rapidTournament = new Tournament();
                rapidTournament.setName("Rapid Chess Challenge");
                rapidTournament.setDescription("Giải đấu cờ vua nhanh với thời gian suy nghĩ giới hạn 15 phút mỗi người");
                rapidTournament.setOrganizerId(2L);  // Player1 là người tổ chức
                rapidTournament.setBoardTypeId(1L);  // Bàn cờ vua chuẩn
                rapidTournament.setOrganizingMethodId(2L);  // Phương thức Round Robin
                rapidTournament.setMaxPlayer(16);
                rapidTournament.setStatus("Coming Soon");
                rapidTournament.setStartDate(dateFormat.parse("2025-05-10"));
                rapidTournament.setEndDate(dateFormat.parse("2025-05-12"));

                // Tournament 3 - Giải đấu cờ vua cho người mới
                Tournament beginnerTournament = new Tournament();
                beginnerTournament.setName("Beginner's Chess Tournament");
                beginnerTournament.setDescription("Giải đấu dành cho người mới chơi cờ vua, ELO dưới 1500");
                beginnerTournament.setOrganizerId(3L);  // Player2 là người tổ chức
                beginnerTournament.setBoardTypeId(1L);  // Bàn cờ vua chuẩn
                beginnerTournament.setOrganizingMethodId(3L);  // Phương thức Single Elimination
                beginnerTournament.setMaxPlayer(32);
                beginnerTournament.setStatus("Planning");
                beginnerTournament.setStartDate(dateFormat.parse("2025-07-05"));
                beginnerTournament.setEndDate(dateFormat.parse("2025-07-07"));

                // Tournament 4 - Giải đấu cờ vua online
                Tournament onlineTournament = new Tournament();
                onlineTournament.setName("Online Chess Masters");
                onlineTournament.setDescription("Giải đấu cờ vua trực tuyến với sự tham gia từ mọi nơi trên thế giới");
                onlineTournament.setOrganizerId(1L);  // Admin là người tổ chức
                onlineTournament.setBoardTypeId(2L);  // Bàn cờ vua online
                onlineTournament.setOrganizingMethodId(4L);  // Phương thức Arena
                onlineTournament.setMaxPlayer(128);
                onlineTournament.setStatus("Registration Open");
                onlineTournament.setStartDate(dateFormat.parse("2025-04-20"));
                onlineTournament.setEndDate(dateFormat.parse("2025-04-22"));

                // Tournament 5 - Giải đấu cờ vua nữ
                Tournament womenTournament = new Tournament();
                womenTournament.setName("Women's Chess Championship");
                womenTournament.setDescription("Giải đấu cờ vua dành riêng cho kỳ thủ nữ");
                womenTournament.setOrganizerId(3L);  // Player2 (nữ) là người tổ chức
                womenTournament.setBoardTypeId(1L);  // Bàn cờ vua chuẩn
                womenTournament.setOrganizingMethodId(1L);  // Phương thức Swiss
                womenTournament.setMaxPlayer(32);
                womenTournament.setStatus("Upcoming");
                womenTournament.setStartDate(dateFormat.parse("2025-08-15"));
                womenTournament.setEndDate(dateFormat.parse("2025-08-22"));

                // Lưu tất cả giải đấu vào cơ sở dữ liệu
                tournamentRepository.save(internationalTournament);
                tournamentRepository.save(rapidTournament);
                tournamentRepository.save(beginnerTournament);
                tournamentRepository.save(onlineTournament);
                tournamentRepository.save(womenTournament);

                System.out.println("Đã khởi tạo 5 bản ghi Tournament vào database");
            }
        };
    }
}