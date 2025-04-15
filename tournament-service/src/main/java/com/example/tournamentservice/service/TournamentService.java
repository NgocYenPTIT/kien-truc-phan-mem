package com.example.tournamentservice.service;

import com.example.tournamentservice.DTOs.CreateTournamentRequestDto;
import com.example.tournamentservice.DTOs.ErrorResponseDto;
import com.example.tournamentservice.constants.CreateTournamentErrorCodes;
import com.example.tournamentservice.constants.TournamentStatus;
import com.example.tournamentservice.model.BoardType;
import com.example.tournamentservice.model.OrganizingMethod;
import com.example.tournamentservice.model.Tournament;
import com.example.tournamentservice.repository.BoardTypeRepository;
import com.example.tournamentservice.repository.OrganizingMethodRepository;
import com.example.tournamentservice.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final OrganizingMethodRepository organizingMethodRepository;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // Pattern cho định dạng "dd/MM/yyyy HH:mm" hoặc "d/M/yyyy H:m"
    private final Pattern datePattern = Pattern.compile(
            "^([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})\\s([0-9]{1,2}):([0-9]{1,2})$");

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository,
                             BoardTypeRepository boardTypeRepository,
                             OrganizingMethodRepository organizingMethodRepository) {
        this.tournamentRepository = tournamentRepository;
        this.boardTypeRepository = boardTypeRepository;
        this.organizingMethodRepository = organizingMethodRepository;
    }

    @Transactional
    public ResponseEntity<?> create(CreateTournamentRequestDto createTournamentDto) {
        List<ErrorResponseDto> errors = new ArrayList<>();

        // Kiểm tra tên giải đấu có trùng trong DB không
        if (tournamentRepository.existsByName(createTournamentDto.getName())) {
            errors.add(new ErrorResponseDto(
                    "name",
                    "Tên giải đấu đã tồn tại",
                    CreateTournamentErrorCodes.NAME_ALREADY_EXISTS  // T001: Tên giải đấu đã tồn tại
            ));
        }

        // Kiểm tra boardTypeId có tồn tại không
        Long boardTypeId = createTournamentDto.getBoardTypeId();
        boolean boardTypeExists = false;

        if (boardTypeId == null) {
            errors.add(new ErrorResponseDto(
                    "boardTypeId",
                    "BoardTypeId không được để trống",
                    CreateTournamentErrorCodes.BOARD_TYPE_NOT_FOUND  // T501: BoardType không tồn tại
            ));
        } else {
            Optional<BoardType> boardType = boardTypeRepository.findById(boardTypeId);
            if (boardType.isPresent()) {
                // Kiểm tra xem BoardType có active không
                if (boardType.get().getIsActive() != null && !boardType.get().getIsActive()) {
                    errors.add(new ErrorResponseDto(
                            "boardTypeId",
                            "BoardType không hoạt động",
                            CreateTournamentErrorCodes.BOARD_TYPE_INACTIVE  // Thêm mã lỗi nếu cần
                    ));
                } else {
                    boardTypeExists = true;
                }
            } else {
                errors.add(new ErrorResponseDto(
                        "boardTypeId",
                        "BoardType không tồn tại",
                        CreateTournamentErrorCodes.BOARD_TYPE_NOT_FOUND  // T501: BoardType không tồn tại
                ));
            }
        }

        // Kiểm tra organizingMethodId có tồn tại không
        Long organizingMethodId = createTournamentDto.getOrganizingMethodId();
        boolean organizingMethodExists = false;

        if (organizingMethodId == null) {
            errors.add(new ErrorResponseDto(
                    "organizingMethodId",
                    "OrganizingMethodId không được để trống",
                    CreateTournamentErrorCodes.ORGANIZING_METHOD_NOT_FOUND  // T502: OrganizingMethod không tồn tại
            ));
        } else {
            Optional<OrganizingMethod> organizingMethod = organizingMethodRepository.findById(organizingMethodId);
            if (organizingMethod.isPresent()) {
                organizingMethodExists = true;

                // Kiểm tra số người tham gia tối đa phù hợp với phương thức tổ chức
                Integer maxPlayer = createTournamentDto.getMaxPlayer();
                if (maxPlayer != null && maxPlayer > 0) {
                    int methodSize = organizingMethod.get().getSize();
                    // Thêm logic kiểm tra nếu cần, ví dụ:
                    // Nếu size=7 vòng đấu nhưng số người chơi quá đông
                    if (methodSize == 7 && maxPlayer > 128) {
                        errors.add(new ErrorResponseDto(
                                "maxPlayer",
                                "Số lượng người chơi quá lớn cho 7 vòng đấu, tối đa 128 người",
                                CreateTournamentErrorCodes.MAX_PLAYER_TOO_LARGE  // Thêm mã lỗi nếu cần
                        ));
                    }
                }
            } else {
                errors.add(new ErrorResponseDto(
                        "organizingMethodId",
                        "OrganizingMethod không tồn tại",
                        CreateTournamentErrorCodes.ORGANIZING_METHOD_NOT_FOUND  // T502: OrganizingMethod không tồn tại
                ));
            }
        }

        // Kiểm tra maxPlayer phải > 0 và là số chẵn
        Integer maxPlayer = createTournamentDto.getMaxPlayer();
        if (maxPlayer == null || maxPlayer <= 0) {
            errors.add(new ErrorResponseDto(
                    "maxPlayer",
                    "Số lượng người chơi phải lớn hơn 0",
                    CreateTournamentErrorCodes.MAX_PLAYER_LESS_THAN_ONE  // T401: Số người chơi phải lớn hơn 0
            ));
        } else if (maxPlayer % 2 != 0) {
            errors.add(new ErrorResponseDto(
                    "maxPlayer",
                    "Số lượng người chơi phải là số chẵn",
                    CreateTournamentErrorCodes.MAX_PLAYER_NOT_EVEN  // T402: Số người chơi phải là số chẵn
            ));
        }

        // Validate và parse startDate
        String startDateStr = createTournamentDto.getStartDate();
        String endDateStr = createTournamentDto.getEndDate();

        Date startDate = null;
        Date endDate = null;

        // Kiểm tra định dạng startDate
        if (startDateStr == null || startDateStr.trim().isEmpty()) {
            errors.add(new ErrorResponseDto(
                    "startDate",
                    "Ngày bắt đầu không được để trống",
                    CreateTournamentErrorCodes.START_DATE_EMPTY  // T101: Ngày bắt đầu không được để trống
            ));
        } else if (!datePattern.matcher(startDateStr).matches()) {
            errors.add(new ErrorResponseDto(
                    "startDate",
                    "Ngày bắt đầu phải có định dạng dd/MM/yyyy HH:mm",
                    CreateTournamentErrorCodes.START_DATE_INVALID_FORMAT  // T102: Ngày bắt đầu không đúng định dạng
            ));
        } else {
            try {
                // Chuẩn hóa định dạng ngày (đảm bảo đủ số 0 ở đầu)
                startDate = dateFormatter.parse(startDateStr);
                // Format lại để đảm bảo định dạng chuẩn dd/MM/yyyy HH:mm
                startDateStr = dateFormatter.format(startDate);
            } catch (ParseException e) {
                errors.add(new ErrorResponseDto(
                        "startDate",
                        "Ngày bắt đầu không hợp lệ",
                        CreateTournamentErrorCodes.START_DATE_PARSE_ERROR  // T103: Lỗi khi parse ngày bắt đầu
                ));
            }
        }

        // Kiểm tra định dạng endDate
        if (endDateStr == null || endDateStr.trim().isEmpty()) {
            errors.add(new ErrorResponseDto(
                    "endDate",
                    "Ngày kết thúc không được để trống",
                    CreateTournamentErrorCodes.END_DATE_EMPTY  // T201: Ngày kết thúc không được để trống
            ));
        } else if (!datePattern.matcher(endDateStr).matches()) {
            errors.add(new ErrorResponseDto(
                    "endDate",
                    "Ngày kết thúc phải có định dạng dd/MM/yyyy HH:mm",
                    CreateTournamentErrorCodes.END_DATE_INVALID_FORMAT  // T202: Ngày kết thúc không đúng định dạng
            ));
        } else {
            try {
                // Chuẩn hóa định dạng ngày
                endDate = dateFormatter.parse(endDateStr);
                // Format lại để đảm bảo định dạng chuẩn dd/MM/yyyy HH:mm
                endDateStr = dateFormatter.format(endDate);
            } catch (ParseException e) {
                errors.add(new ErrorResponseDto(
                        "endDate",
                        "Ngày kết thúc không hợp lệ",
                        CreateTournamentErrorCodes.END_DATE_PARSE_ERROR  // T203: Lỗi khi parse ngày kết thúc
                ));
            }
        }

        // Kiểm tra logic nếu cả hai ngày đều hợp lệ
        if (startDate != null && endDate != null) {
            // Kiểm tra startDate phải trước endDate
            if (!startDate.before(endDate)) {
                errors.add(new ErrorResponseDto(
                        "dateRange",
                        "Ngày bắt đầu phải trước ngày kết thúc",
                        CreateTournamentErrorCodes.START_DATE_AFTER_END_DATE  // T301: Ngày bắt đầu sau ngày kết thúc
                ));
            }

            // Kiểm tra ngày hiện tại phải trước startDate
            if (!new Date().before(startDate)) {
                errors.add(new ErrorResponseDto(
                        "startDate",
                        "Ngày bắt đầu phải sau ngày hiện tại",
                        CreateTournamentErrorCodes.START_DATE_IN_PAST  // T104: Ngày bắt đầu nằm trong quá khứ
                ));
            }

            // Kiểm tra khoảng thời gian giải đấu có hợp lý không
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

            if (boardTypeExists && organizingMethodExists) {
                // Ví dụ: Giải đấu 7 vòng cần ít nhất 2 ngày
                Optional<OrganizingMethod> method = organizingMethodRepository.findById(organizingMethodId);
                if (method.isPresent() && method.get().getSize() > 7 && diffInDays < 2) {
                    errors.add(new ErrorResponseDto(
                            "dateRange",
                            "Giải đấu với " + method.get().getSize() + " vòng cần ít nhất 2 ngày",
                            CreateTournamentErrorCodes.TOURNAMENT_DURATION_TOO_SHORT  // Thêm mã lỗi nếu cần
                    ));
                }
            }
        }

        // Trả về lỗi nếu có
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        // Nếu không có lỗi, tiếp tục xử lý và lưu vào DB
        Tournament tournament = new Tournament();
        tournament.setName(createTournamentDto.getName());
        tournament.setDescription(createTournamentDto.getDescription());
        tournament.setOrganizerId(createTournamentDto.getOrganizerId());
        tournament.setBoardTypeId(createTournamentDto.getBoardTypeId());
        tournament.setOrganizingMethodId(createTournamentDto.getOrganizingMethodId());
        tournament.setMaxPlayer(createTournamentDto.getMaxPlayer());
        tournament.setStartDate(startDateStr);  // Đã được chuẩn hóa
        tournament.setEndDate(endDateStr);      // Đã được chuẩn hóa
        tournament.setStatus(TournamentStatus.NOT_STARTED);  // Trạng thái mặc định là chưa bắt đầu

        Tournament savedTournament = tournamentRepository.save(tournament);

        return ResponseEntity.ok(savedTournament);
    }
}