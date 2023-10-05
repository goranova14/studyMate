package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteNotificationUseCaseImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private DeleteNotificationCaseImpl deleteNotificationCase;

    @Test
    void deleteNotificationShouldReturnVoid() {

        NotificationEntity notificationEntity = NotificationEntity.builder().id(1L).build();
        when(notificationRepository.existsById(notificationEntity.getId())).thenReturn(true);
        deleteNotificationCase.deleteNotification(notificationEntity.getId());
        verify(notificationRepository).existsById(notificationEntity.getId());

        when(notificationRepository.existsById(notificationEntity.getId())).thenReturn(false);
        verify(notificationRepository).existsById(notificationEntity.getId());
        assertFalse(notificationRepository.existsById(notificationEntity.getId()));

    }

    @Test
    void deleteNotificationShouldThrowAnInvalidStudentException() {
        NotificationEntity notificationEntity = NotificationEntity.builder().id(1L).build();
        when(notificationRepository.existsById(notificationEntity.getId())).thenReturn(false);
        assertThrows(InvalidEntityException.class, () -> deleteNotificationCase.deleteNotification(notificationEntity.getId()));
        verify(notificationRepository).existsById(notificationEntity.getId());
    }
}