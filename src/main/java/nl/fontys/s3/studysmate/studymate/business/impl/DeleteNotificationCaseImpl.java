package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.DeleteNotificationUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Response;
import nl.fontys.s3.studysmate.studymate.persistence.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteNotificationCaseImpl implements DeleteNotificationUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public Response deleteNotification(long id) {

        boolean exists = notificationRepository.existsById(id);
        if (!exists) {
            throw new InvalidEntityException("No notification found with id:" + id);

        } else {
            this.notificationRepository.deleteById(id);
            return Response.builder().message("Successfully deleted").build();

        }
    }
}
