package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Grade;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradeConverterTest {

    @Test
    void convertPositive() {
        UserEntity user=UserEntity.builder()   .userRoles(Set.of(
                UserRoleEntity.builder()
                        .role(Roles.STUDENT)
                        .build()
        )).build();
        GradeEntity gradeEntity=GradeEntity.builder()
                .grade(1)
                .userEntity(user)
                .assignmentEntity(new AssignmentEntity())
                .id(1l)
                .build();

        Grade grade=GradeConverter.convert(gradeEntity);
        assertEquals(gradeEntity.getGrade(),grade.getGradeNum());
    }
}