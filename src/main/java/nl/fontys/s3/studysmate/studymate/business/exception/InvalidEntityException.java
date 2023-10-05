package nl.fontys.s3.studysmate.studymate.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEntityException extends ResponseStatusException {
    public InvalidEntityException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }


}
