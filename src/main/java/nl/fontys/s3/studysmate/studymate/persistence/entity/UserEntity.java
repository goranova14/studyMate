package nl.fontys.s3.studysmate.studymate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"announcements"})

public class  UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pcn;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;
    private String address;
    private String password;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<UserRoleEntity> userRoles;
    @JsonIgnore
    @OneToMany(mappedBy = "userEntity",fetch = FetchType.EAGER)
    private List<AnnouncementEntity> announcements;
    @Override
    public String toString() {
        return "UserEntity{" +
                "pcn=" + pcn +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
