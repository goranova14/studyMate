package nl.fontys.s3.studysmate.studymate.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import org.jetbrains.annotations.NotNull;



@Entity
@Table(name = "user_role")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;
}
