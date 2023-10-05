package nl.fontys.s3.studysmate.studymate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "assignements")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"courseEntity", "grades"})
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime submissionDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "assignmentEntity",fetch = FetchType.EAGER)
    private List<GradeEntity> grades;

}
