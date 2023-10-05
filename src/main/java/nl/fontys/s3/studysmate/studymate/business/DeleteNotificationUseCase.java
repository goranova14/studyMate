package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.Response;

public interface DeleteNotificationUseCase {
    Response deleteNotification(long id);
}
