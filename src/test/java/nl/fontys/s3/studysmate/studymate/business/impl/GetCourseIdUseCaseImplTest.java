package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.GetCourseUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Course;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCourseIdUseCaseImplTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetCourseIdUseCaseImpl getCourseUseCase;

    @Test
    void getCourse() {
        CourseEntity courseEntity = CourseEntity.builder()
                .id(1L)
                .name("course")
                .users(new ArrayList<>())
                .build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        Optional<Course> foundCourse = getCourseUseCase.getCourse(1L);
        assertTrue(foundCourse.isPresent());
        verify(courseRepository).findById(1L);
    }

    @Test
    void getCourseNegative() {

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> getCourseUseCase.getCourse(1L));
        verify(courseRepository).findById(1L);

    }


}