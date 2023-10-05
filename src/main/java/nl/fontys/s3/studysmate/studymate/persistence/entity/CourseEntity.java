package nl.fontys.s3.studysmate.studymate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"announcements", "users", "assignments"})

public class CourseEntity {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @OneToMany(mappedBy = "courseEntity",fetch = FetchType.EAGER)
    private List<AnnouncementEntity> announcements;

    @OneToMany(mappedBy = "courseEntity",fetch = FetchType.EAGER)
    private List<UserEntity> users;
    @OneToMany(mappedBy = "courseEntity",fetch = FetchType.EAGER)
    private List<AssignmentEntity> assignments;



    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
