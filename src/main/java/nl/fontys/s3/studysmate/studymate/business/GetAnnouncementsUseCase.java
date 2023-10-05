package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsResponse;

public interface GetAnnouncementsUseCase {
    GetAllAnnouncementsResponse getAnnouncements(GetAllAnnouncementsRequest request);
}
