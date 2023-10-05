package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UpdateAnnouncementUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.UpdateAnnouncementRequest;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAnnouncementUseCaseImpl implements UpdateAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;
    private AccessToken requestAccessToken;

    @Override
    public void updateAnnouncement(UpdateAnnouncementRequest request) {

        Optional<AnnouncementEntity> announcement = announcementRepository.findById(request.getId());
        if (announcement.isPresent()) {
            if (announcement.get().getUserEntity().getPcn().equals( requestAccessToken.getUserId())) {
                AnnouncementEntity newAnnouncement = announcement.get();
                newAnnouncement.setTitle(request.getTitle());
                newAnnouncement.setDescription(request.getDescription());
                announcementRepository.save(newAnnouncement);
            }
            else{
                throw new InvalidEntityException("Only user who submitted the announcement can edit it");

            }
        } else {
            throw new InvalidEntityException("INVALID_ID");
        }
    }
}
