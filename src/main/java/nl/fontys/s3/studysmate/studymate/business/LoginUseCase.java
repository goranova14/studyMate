package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.LoginRequest;
import nl.fontys.s3.studysmate.studymate.domain.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);

}
