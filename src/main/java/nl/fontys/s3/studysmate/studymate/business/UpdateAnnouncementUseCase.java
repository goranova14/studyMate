package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.UpdateAnnouncementRequest;

public interface UpdateAnnouncementUseCase {
    void updateAnnouncement(UpdateAnnouncementRequest request);
}
