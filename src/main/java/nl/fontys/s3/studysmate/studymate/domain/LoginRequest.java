package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
