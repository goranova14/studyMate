package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGradeRequest {
    private Long id;
    @NotNull
    private int gradeNum;
    @NotNull
    private UserEntity user;
    @NotNull

    private AssignmentEntity assignment;



}
