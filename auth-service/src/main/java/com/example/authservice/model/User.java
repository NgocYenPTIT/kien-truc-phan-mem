package com.example.authservice.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private boolean gender;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive = true; // Thiết lập giá trị mặc định là true

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccess;

    private int elo;

    // Thêm các trường audit
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    // Method này sẽ tự động được gọi trước khi entity được lưu vào DB lần đầu
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (updatedAt == null) {
            updatedAt = new Date();
        }
    }

    // Method này sẽ tự động được gọi trước khi entity được cập nhật
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    // Method để soft delete user
    public void delete() {
        this.deletedAt = new Date();
        this.isActive = false;
    }

    // Method để check xem user đã bị xóa chưa
    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
