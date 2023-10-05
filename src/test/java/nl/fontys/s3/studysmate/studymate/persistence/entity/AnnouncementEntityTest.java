package nl.fontys.s3.studysmate.studymate.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AnnouncementEntityTest {

    @Test
    void testToString() {
       AnnouncementEntity announcementEntity = AnnouncementEntity.builder()
                .id(1l)
                .description("s")
                .title("sa")
                .build();
        assertNotNull(announcementEntity);
        assertEquals("AnnouncementEntity{" +
                "id=" + announcementEntity.getId() +
                ", title='" + announcementEntity.getTitle() + '\'' +
                ", description='" + announcementEntity.getDescription() + '\'' +
                '}', announcementEntity.toString());
    }
}