package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetAssignmentsUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetNotificationsUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetNotificationsUseCaseImpl implements GetNotificationsUseCase {
    private NotificationRepository notificationRepository;

    @Override
    public GetAllNotificationsResponse getNotifications(Long studentPcn) {
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByUserEntity_Pcn(studentPcn);
        final  GetAllNotificationsResponse response=new GetAllNotificationsResponse();
        List<Notification> results=notificationEntities.stream()
                .map(NotificationConverter::convert)
                .toList();
        response.setNotificationList(results);
        if (results.isEmpty()){
            throw new InvalidEntityException("No notifications found");

        }
        else {
            return  response;
        }    }
}
