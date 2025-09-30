package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String addrCountry;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroupMemberEntity> memberships = new HashSet<>();
    // Expenses that this user has paid
    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<ExpenseEntity> paidExpenses = new ArrayList<>();

    // Shares assigned to this user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ExpenseShareEntity> expenseShares = new ArrayList<>();
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<SettlementEntity> settlementsPaid = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<SettlementEntity> settlementsReceived = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}