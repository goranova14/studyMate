package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateAnnouncementRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateAnnouncementResponse;

public interface CreateAnnouncementUseCase {
    CreateAnnouncementResponse createAnnouncement(CreateAnnouncementRequest request);
}
