package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Assignment;
import nl.fontys.s3.studysmate.studymate.domain.Notification;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;

public class NotificationConverter {

    public static Notification convert(NotificationEntity notificationEntity) {

        return Notification.builder()
                .assignmentId(notificationEntity.getAssignmentEntity().getId())
                .userPcn(notificationEntity.getUserEntity().getPcn())
                .submissionDate(notificationEntity.getSubmissionDate()).id(notificationEntity.getId())
                .build();


    }

    private NotificationConverter() {
    }
}
