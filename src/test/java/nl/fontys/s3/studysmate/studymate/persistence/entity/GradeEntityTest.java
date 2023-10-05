package nl.fontys.s3.studysmate.studymate.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeEntityTest {

    @Test
    void testToString() {
        GradeEntity gradeEntity= GradeEntity.builder().id(1l).grade(1).build();

        assertNotNull(gradeEntity);
        assertEquals( "GradeEntity{" +
                "id=" + gradeEntity.getId() +
                ", grade=" + gradeEntity.getGrade() +
                '}',gradeEntity.toString());



    }
}