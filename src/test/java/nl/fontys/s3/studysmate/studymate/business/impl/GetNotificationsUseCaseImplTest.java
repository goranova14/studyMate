package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNotificationsUseCaseImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private GetNotificationsUseCaseImpl getNotificationsUseCase;

    @Test
    void getNotification() {
        UserEntity user=UserEntity.builder().email("deni@abv.bg").build();
        AssignmentEntity assignmentEntity=AssignmentEntity.builder().id(1l).build();
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .userEntity(user)
                .id(1l)
                .assignmentEntity(assignmentEntity)
                .build();
        NotificationEntity notificationEntity1 = NotificationEntity.builder()
                .userEntity(user)
                .assignmentEntity(assignmentEntity)
                .id(2l)
                .build();

        when(notificationRepository.findAllByUserEntity_Pcn(1L))
                .thenReturn(List.of(notificationEntity, notificationEntity1));

        GetAllNotificationsResponse response = getNotificationsUseCase.getNotifications(1l);
        List<Notification> notificationList = response.getNotificationList();

        assertEquals(2, notificationList.size());
        verify(notificationRepository).findAllByUserEntity_Pcn(1L);
    }

    @Test
    void getNotificationsShouldThrowAnException(){
        when(notificationRepository.findAllByUserEntity_Pcn(1L)).thenReturn(Collections.emptyList());
        assertThrows(InvalidEntityException.class, ()
                -> getNotificationsUseCase.getNotifications(1l));
        verify(notificationRepository).findAllByUserEntity_Pcn(1L);
    }
}