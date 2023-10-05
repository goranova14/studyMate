package nl.fontys.s3.studysmate.studymate.configuration.auth;

import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestAuthenticatedUserProviderTest {
    private final RequestAuthenticatedUserProvider requestAuthenticatedUserProvider = new RequestAuthenticatedUserProvider();

    @Test
    void getAuthenticatedUserInRequestNegative() {
        SecurityContextHolder.clearContext();

        AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();

        assertNull(accessToken);
    }  @Test
    void getAuthenticatedUserInRequestPositive() {
        SecurityContextHolder.clearContext();
        AccessToken expectedAccessToken = AccessToken.builder().build();
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getDetails()).thenReturn(expectedAccessToken);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        AccessToken actualAccessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();

        assertEquals(expectedAccessToken, actualAccessToken);
    }

}