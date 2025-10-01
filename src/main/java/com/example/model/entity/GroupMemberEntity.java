package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "group_members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"group_id", "user_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "group_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_group_member_group")
    )
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_group_member_user")
    )
    private UserEntity user;
    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMemberEntity that)) return false;
        return group != null && user != null &&
                group.getId() != null && user.getId() != null &&
                group.getId().equals(that.getGroup().getId()) &&
                user.getId().equals(that.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(group != null ? group.getId() : null,
                user != null ? user.getId() : null);
    }

}
