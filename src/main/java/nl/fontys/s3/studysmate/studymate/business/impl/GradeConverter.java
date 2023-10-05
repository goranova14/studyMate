package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Grade;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
final class GradeConverter {

    public static Grade convert(GradeEntity gradeEntity) {
        return Grade.builder()
                .gradeNum(gradeEntity.getGrade())
                .id(gradeEntity.getId())
                .assignmentId(gradeEntity.getAssignmentEntity().getId())
                .userPcn(gradeEntity.getUserEntity().getPcn())
                .submissionDate(gradeEntity.getSubmissionDate())
                .build();
    }

    private GradeConverter() {
    }
}

