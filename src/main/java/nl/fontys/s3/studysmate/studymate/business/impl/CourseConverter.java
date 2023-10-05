package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Course;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;

import java.util.stream.Collectors;
final class CourseConverter {

    public static Course convert(CourseEntity courseEntity) {
        return Course.builder()
                .name(courseEntity.getName())
                .announcements(courseEntity.getAnnouncements())
                .description(courseEntity.getDescription())
                .id(courseEntity.getId())
                .users(courseEntity.getUsers().stream().map(UserConverter::convert).collect(Collectors.toList()))
                .build();
    }

    private CourseConverter() {
    }
}

