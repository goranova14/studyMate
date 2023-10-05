package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {
    @Mock
    private CreateNotificationUseCase createNotificationUseCase;
    @Mock
    private GetNotificationsUseCase getNotificationsUseCase;
    @Mock
    private DeleteNotificationUseCase deleteNotificationUseCase;
    @InjectMocks
    private NotificationController notificationController;

    @Test
    void createNotification() {
        CreateNotificationRequest request = CreateNotificationRequest.builder().assignmentId(1l).userId(2l).build();
        Response response = Response.builder().message("Successfully added").build();
        when(createNotificationUseCase.createNotification(request)).thenReturn(response);
        ResponseEntity<Response> actual = notificationController.createNotification(request);
        assertEquals(actual.getBody().getMessage(), response.getMessage());
        verify(createNotificationUseCase).createNotification(request);
    }

//    @Test
//    void deleteNotification() {
//        ResponseEntity<Void> response = notificationController.deleteNotification(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    void getNotification() {
        Notification notification = Notification.builder().id(1l).submissionDate(LocalDateTime.now()).userPcn(1l).assignmentId(2l).build();
        Notification notification1 = Notification.builder().id(2l).submissionDate(LocalDateTime.now()).userPcn(1l).assignmentId(2l).build();
        GetAllNotificationsResponse response = GetAllNotificationsResponse.builder().notificationList(List.of(notification1, notification)).build();
        when(getNotificationsUseCase.getNotifications(1l)).thenReturn(response);
        ResponseEntity<GetAllNotificationsResponse> actual = notificationController.getAllNotificationsForUser(1l);
        assertEquals(response.getNotificationList(),actual.getBody().getNotificationList());
        verify(getNotificationsUseCase).getNotifications(1l);
    }

}