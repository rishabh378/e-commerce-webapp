package com.pesto.ecommerce.entity;

import com.pesto.ecommerce.enums.ContactType;
import com.pesto.ecommerce.enums.OtpType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_info")
@Data
public class OtpInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_contact", nullable = false)
    private String userContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType contactType; // Email or phone

    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type")
    private OtpType otpType;   // LOGIN, FORGOT_PASSWORD

    @Column(name = "otp")
    private String otp;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
