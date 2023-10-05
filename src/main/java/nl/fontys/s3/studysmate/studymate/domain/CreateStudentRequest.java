package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequest {

    private Long pcn;

    @NotBlank
    private String firstName;


    @NotBlank
    private String lastName;

    @NotBlank

    private String address;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private Course course;

}
