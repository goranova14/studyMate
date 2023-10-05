package nl.fontys.s3.studysmate.studymate.persistence.entity;

import nl.fontys.s3.studysmate.studymate.domain.Notification;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationEntityTest {

    @Test
    void testToString() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        UserEntity userEntity = UserEntity.builder().pcn(2l).build();
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .id(1l)
                .assignmentEntity(assignmentEntity)
                .userEntity(userEntity).submissionDate(LocalDateTime.now()).build();
        assertNotNull(notificationEntity);
        assertEquals("NotificationEntity{" +
                "id=" + notificationEntity.getId() +
                '}', notificationEntity.toString());
    }
}