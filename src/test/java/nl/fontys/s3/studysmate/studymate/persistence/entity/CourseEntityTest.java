package nl.fontys.s3.studysmate.studymate.persistence.entity;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class CourseEntityTest {

    @Test
    void testToString() {
       CourseEntity courseEntity = CourseEntity.builder()
                .id(1l)
                .name("s")
                .description("sa")
                .users(null)
                .build();
        assertNotNull(courseEntity);
        assertEquals("CourseEntity{" +
                "id=" + courseEntity.getId() +
                ", name='" + courseEntity.getName() + '\'' +
                ", description='" + courseEntity.getDescription() + '\'' +
                '}', courseEntity.toString());
        assertEquals(null, courseEntity.getUsers());
    }
}