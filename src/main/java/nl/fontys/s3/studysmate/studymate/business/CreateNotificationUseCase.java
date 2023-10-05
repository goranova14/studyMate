package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateNotificationRequest;
import nl.fontys.s3.studysmate.studymate.domain.Response;

public interface CreateNotificationUseCase {
    Response createNotification(CreateNotificationRequest request);
}
