package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.AccessToken;

public interface AccessTokenEncoder {

    String encode(AccessToken accessToken);
}
