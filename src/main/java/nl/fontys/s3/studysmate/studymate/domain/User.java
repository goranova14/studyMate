package nl.fontys.s3.studysmate.studymate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long pcn;
    private CourseEntity course;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String email;
    private Roles role;

}
