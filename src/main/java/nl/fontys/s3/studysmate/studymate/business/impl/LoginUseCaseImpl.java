package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.RequiredArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.AccessTokenEncoder;
import nl.fontys.s3.studysmate.studymate.business.LoginUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.LoginRequest;
import nl.fontys.s3.studysmate.studymate.domain.LoginResponse;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final AccessTokenEncoderDecoderImpl accessTokenEncoderDecoderImpl;
    private final UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            throw new InvalidEntityException("Invalid user");
        }
        UserEntity foundUser=user.get();
        if (!matchesPassword(loginRequest.getPassword(), foundUser.getPassword())) {
            throw new InvalidEntityException("Password is incorrect");
        }

        String accessToken = generateAccessToken(foundUser);
        return LoginResponse.builder().accessToken(accessToken).build();
    }
    private boolean matchesPassword(String rawPassword, String encodedPassword) {
      return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getEmail())
                        .roles(roles)
                        .userId(user.getPcn())
                        .build());
    }
}
