package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Announcement;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;

public class AnnouncementConverter {

    public static Announcement convert(AnnouncementEntity announcement){

        return  Announcement.builder()
                .courseEntityId(announcement.getCourseEntity().getId())
                .description(announcement.getDescription())
                .title(announcement.getTitle())
                .id(announcement.getId())
                .submissionDate(announcement.getSubmissionDate())
                .userEntityEmail(announcement.getUserEntity().getEmail())
                .build();
    }

    private AnnouncementConverter() {

    }
}
