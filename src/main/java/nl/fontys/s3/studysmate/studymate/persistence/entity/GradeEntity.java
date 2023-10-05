package nl.fontys.s3.studysmate.studymate.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "grades")
@AllArgsConstructor
@NoArgsConstructor
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignment")
    private AssignmentEntity assignmentEntity;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private UserEntity userEntity;
    @Builder.Default
    private int grade=0;

    private LocalDateTime submissionDate;
    private String assignmentUrl;
    @Override
    public String toString() {
        return "GradeEntity{" +
                "id=" + id +
                ", grade=" + grade +
                '}';
    }
}
