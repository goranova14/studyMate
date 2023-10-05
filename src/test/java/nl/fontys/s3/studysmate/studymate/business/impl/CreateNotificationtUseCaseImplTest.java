package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.CreateNotificationRequest;
import nl.fontys.s3.studysmate.studymate.domain.Response;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNotificationtUseCaseImplTest {
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private CreateNotificationUseCaseImpl createNotificationUseCase;

//    @Test
//    void createNotification() {
//
//        CreateNotificationRequest request = new CreateNotificationRequest();
//        request.setAssignmentId(1l);
//        request.setUserId(2l);
//        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1L).build();
//        UserEntity user = UserEntity.builder().pcn(2l).build();
//        NotificationEntity notificationEntity = NotificationEntity.builder().id(10l).assignmentEntity(assignmentEntity).userEntity(user).submissionDate(LocalDateTime.now()).build();
//        when(assignmentRepository.findById(request.getAssignmentId())).thenReturn(Optional.of(assignmentEntity));
//        when(userRepository.findByPcn(request.getUserId())).thenReturn(Optional.of(user));
//        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);
//
//        Response response = createNotificationUseCase.createNotification(request);
//        assertNotNull(response);
//        verify(assignmentRepository).findById(request.getAssignmentId());
//        verify(userRepository).findByPcn(request.getUserId());
//    }
    @Test
    void createNotificationNegativeForAssignment(){
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setAssignmentId(1l);
        request.setUserId(2l);
        when(assignmentRepository.findById(1l)).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> createNotificationUseCase.createNotification(request));
        verify(assignmentRepository).findById(request.getAssignmentId());

    }@Test
    void createNotificationNegativeForUser(){
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setAssignmentId(1l);
        request.setUserId(2l);
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1L).build();

        when(assignmentRepository.findById(1l)).thenReturn(Optional.of(assignmentEntity));
        when(userRepository.findByPcn(request.getUserId())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> createNotificationUseCase.createNotification(request));
        verify(assignmentRepository).findById(request.getAssignmentId());

    }



}