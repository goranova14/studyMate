package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Announcement;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnouncementConverterTest {

    @Test
    void convertPositive() {
        UserEntity user= UserEntity.builder().email("s$avc").build();
        CourseEntity courseEntity= CourseEntity.builder().id(1l).build();
        AnnouncementEntity announcementEntity=AnnouncementEntity.builder()
                .title("Denitsa")
                .userEntity(user)
                .courseEntity(courseEntity)
                .description("de")
                .build();

        Announcement announcement=AnnouncementConverter.convert(announcementEntity);
        assertEquals(announcementEntity.getTitle(),announcement.getTitle());
    }
}