package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateGradeRequest;
import nl.fontys.s3.studysmate.studymate.domain.Response;

public interface CreateEmailUseCase {
    Response sendEmail(String subject,String to,String text);
}
