package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.DeleteAnnouncementUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteAnnouncementCaseImpl implements DeleteAnnouncementUseCase {

    private final AnnouncementRepository announcementRepository;
    private AccessToken requestAccessToken;


    @Override
    public void deleteAnnouncement(long id) {
        Optional<AnnouncementEntity> announcement = announcementRepository.findById(id);
        if (announcement.isEmpty()) {
            throw new InvalidEntityException("No announcement found with id:" + id);

        } else {
            if (announcement.get().getUserEntity().getPcn()==requestAccessToken.getUserId()){
            this.announcementRepository.deleteById(id);

            }
            else {
                throw new InvalidEntityException("Only user who submitted the announcement can delete it");

            }

        }
    }
}
