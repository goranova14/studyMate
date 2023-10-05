package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllNotificationsResponse;

public interface GetNotificationsUseCase {
    GetAllNotificationsResponse getNotifications(Long studentPcn);
}
