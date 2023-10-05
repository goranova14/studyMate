package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetAnnouncementsUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Announcement;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAnnouncementsUseCaseImpl implements GetAnnouncementsUseCase {
    private AnnouncementRepository announcementRepository;

    @Override
    public GetAllAnnouncementsResponse getAnnouncements(GetAllAnnouncementsRequest request) {
        List<AnnouncementEntity> announcementEntities = announcementRepository.findAllByCourseEntity_Id(request.getCourseId());
        final GetAllAnnouncementsResponse response = new GetAllAnnouncementsResponse();
        List<Announcement> result = announcementEntities
                .stream()
                .map(AnnouncementConverter::convert)
                .toList();
        response.setAnnouncementEntityList(result);

        if (result.isEmpty()) {
            throw new InvalidEntityException("No announcements found");

        } else {
            return response;
        }
    }
}
