package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsResponse;


public interface GetAnnouncementByTitleUseCase {
    GetAllAnnouncementsResponse getAnnouncements(String title, Long courseId);
}
