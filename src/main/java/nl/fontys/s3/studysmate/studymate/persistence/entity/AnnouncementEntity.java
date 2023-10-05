package nl.fontys.s3.studysmate.studymate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "announcements")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"courseEntity", "userEntity"})
public class AnnouncementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String title;
    private LocalDateTime submissionDate;

    @Override
    public String toString() {
        return "AnnouncementEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
