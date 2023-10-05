package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateEmailUseCase;
import nl.fontys.s3.studysmate.studymate.business.CreateNotificationUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.CreateNotificationRequest;
import nl.fontys.s3.studysmate.studymate.domain.Response;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateNotificationUseCaseImpl implements CreateNotificationUseCase {
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final CreateEmailUseCase createEmailUseCase;

    @Override
    public Response createNotification(CreateNotificationRequest request) {

        Optional<AssignmentEntity> assignmentEntity = assignmentRepository.findById(request.getAssignmentId());
        if (assignmentEntity.isEmpty()) {
            throw new InvalidEntityException("User not found");

        }
        Optional<UserEntity> user = userRepository.findByPcn(request.getUserId());
        if (!user.isPresent()) {
            throw new InvalidEntityException("User not found");
        }
        LocalDateTime submissionDate = LocalDateTime.now();

        NotificationEntity notificationEntity = NotificationEntity.builder().userEntity(user.get()).assignmentEntity(assignmentEntity.get()).submissionDate(submissionDate).build();
        notificationRepository.save(notificationEntity);
        createEmailUseCase.sendEmail("Grade ",user.get().getEmail(),"Grade change has been made");
        return Response.builder().message("Successfully added").build();

    }

}
