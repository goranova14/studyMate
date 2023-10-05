package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetAnnouncementByTitleUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Announcement;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAnnouncementsByTitleUseCaseImpl implements GetAnnouncementByTitleUseCase {

    private AnnouncementRepository announcementRepository;

    @Override
    public GetAllAnnouncementsResponse getAnnouncements(String title,Long courseId) {

        List<AnnouncementEntity> announcementEntities=announcementRepository.findByTitleContainingIgnoreCaseAndCourseEntity_Id(title,courseId);
        final GetAllAnnouncementsResponse response = new GetAllAnnouncementsResponse();
        List<Announcement> result = announcementEntities
                .stream()
                .map(AnnouncementConverter::convert)
                .toList();
        response.setAnnouncementEntityList(result);
        if (announcementEntities.isEmpty()) {
            throw new InvalidEntityException("No announcements");

        } else {
            return response;
        }
    }


}
