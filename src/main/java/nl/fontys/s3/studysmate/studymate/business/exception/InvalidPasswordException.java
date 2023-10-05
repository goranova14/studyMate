package nl.fontys.s3.studysmate.studymate.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {

    public InvalidPasswordException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }

}
