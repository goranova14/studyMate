package nl.fontys.s3.studysmate.studymate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseRequest {
    private String name;
   @NotNull
    private Long id;

    private String description;

    private List<UserEntity> users;
}
