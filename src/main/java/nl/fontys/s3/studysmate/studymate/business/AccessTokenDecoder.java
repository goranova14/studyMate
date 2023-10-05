package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessToken);

}
