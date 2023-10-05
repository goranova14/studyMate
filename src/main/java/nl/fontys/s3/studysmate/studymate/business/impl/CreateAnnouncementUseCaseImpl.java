package nl.fontys.s3.studysmate.studymate.business.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateAnnouncementUseCase;
import nl.fontys.s3.studysmate.studymate.business.CreateCourseUseCase;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateAnnouncementUseCaseImpl implements CreateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;

    private AnnouncementEntity createNewAnnouncement(CreateAnnouncementRequest request) {
        LocalDateTime submissionDate=LocalDateTime.now();

        AnnouncementEntity newAnnouncement = AnnouncementEntity.builder()
                .description(request.getDescription())
                .title(request.getTitle())
                .courseEntity(request.getCourse())
                .submissionDate(submissionDate)
                .userEntity(request.getUserEntity())
                .build();

        return announcementRepository.save(newAnnouncement);
    }

    @Override
    public CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request) {
        AnnouncementEntity newAnnouncement = createNewAnnouncement(request);
        return CreateAnnouncementResponse.builder()
                .title(newAnnouncement.getTitle())
                .build();
    }
}
