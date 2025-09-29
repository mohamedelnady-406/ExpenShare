package com.example.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 255, nullable = false, unique = true)
    private String email;
    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "addr_line1", length = 150)
    private String addrLine1;
    @Column(name = "addr_line2", length = 150)
    private String addrLine2;

    @Column(name = "addr_city", length = 80)
    private String addrCity;

    @Column(name = "addr_state", length = 80)
    private String addrState;

    @Column(name = "addr_postal", length = 20)
    private String addrPostal;

    @Column(name = "addr_country", length = 2)
    private String addrCountry; // ISO 2-letter code

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}