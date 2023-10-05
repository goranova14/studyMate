package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Course;
import nl.fontys.s3.studysmate.studymate.domain.GetAllCoursesRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllCoursesResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCoursesUseCaseImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetCoursesUseCaseImpl getCoursesUseCase;

    @Test
    void getCourses() {
        CourseEntity courseEntity1 = CourseEntity.builder().id(1L).users(new ArrayList<>()).build();
        List<CourseEntity> courseEntities = new ArrayList<>();
        courseEntities.add(courseEntity1);

        when(courseRepository.findAll()).thenReturn(courseEntities);


        GetAllCoursesRequest request=GetAllCoursesRequest.builder().build();
        GetAllCoursesResponse response=getCoursesUseCase.getCourses(request);

        assertEquals(1,response.getCourses().size());
        verify(courseRepository).findAll();
    }

    @Test
    void getCoursesNegative(){
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());
        GetAllCoursesRequest request=GetAllCoursesRequest.builder().build();
        assertThrows(InvalidEntityException.class,()->getCoursesUseCase.getCourses(request));
        verify(courseRepository).findAll();

    }
}