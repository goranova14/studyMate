package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Notification;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificatioonConverterTest {

    @Test
    void convertPositive() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .userEntity(new UserEntity())
                .submissionDate(LocalDateTime.now())
                .assignmentEntity(assignmentEntity)
                .build();

        Notification notification = NotificationConverter.convert(notificationEntity);
        assertEquals(notificationEntity.getAssignmentEntity().getId(), notification.getAssignmentId());
    }
}