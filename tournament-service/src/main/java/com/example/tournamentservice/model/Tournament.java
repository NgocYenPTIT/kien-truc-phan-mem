package com.example.tournamentservice.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
    private Long organizerId;

    // Giữ lại khóa ngoại như một thuộc tính bình thường
    @Column(name = "board_type_id")
    private Long boardTypeId;

    // Giữ lại khóa ngoại như một thuộc tính bình thường
    @Column(name = "organizing_method_id")
    private Long organizingMethodId;

    // Thêm quan hệ ManyToOne với BoardType
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BoardType boardType;

    // Thêm quan hệ ManyToOne với OrganizingMethod
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizing_method_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizingMethod organizingMethod;

    private Integer maxPlayer;

    @Column(name = "start_date")
    private String startDate;  // Lưu dưới định dạng DD/MM/YYYY HH:MM

    @Column(name = "end_date")
    private String endDate;    // Lưu dưới định dạng DD/MM/YYYY HH:MM

    @Column(name = "status")
    private String status;     // Trạng thái của giải đấu
}