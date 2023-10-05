package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Assignment;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;

public class AssignmentConverter {

    public static Assignment convert(AssignmentEntity assignmentEntity) {

        return Assignment.builder()
                .description(assignmentEntity.getDescription())
                .id(assignmentEntity.getId())
                .courseEntity(assignmentEntity.getCourseEntity())
                .name(assignmentEntity.getName())
                .deadline(assignmentEntity.getDeadline())
                .build();


    }

    private AssignmentConverter() {
    }
}
