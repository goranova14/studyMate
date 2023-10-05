package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.CreateCourseRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateCourseResponse;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//allows to include the Mockito framework
class CreateCourseUseCaseImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks//injects the mock object into the class
    private CreateCourseUseCaseImpl createCourseUseCase;

    @Test
    void createCoursePositive() {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .name("course")
                .description("description")
                .users(new ArrayList<>())
                .build();
        CourseEntity courseEntity = CourseEntity.builder()
                .id(1L)
                .name("course")
                .description("desc")
                .users(new ArrayList<>())
                .build();

        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        verify(courseRepository).save(any(CourseEntity.class));
        assertEquals(response.getId(), courseEntity.getId());

    }

    @Test
    void CreateNewCoursePositive() {
        UserEntity userEntity=UserEntity.builder()
                .pcn(1l)

                .build();
        List<UserEntity> users=new ArrayList<>();
        users.add(userEntity);
        CreateCourseRequest request = CreateCourseRequest.builder()
                .name("course")
                .description("description")
                .users(users)
                .build();

        CourseEntity courseEntity = CourseEntity.builder()
                .id(1L)
                .name("course")
                .description("desc")
                .users(users)
                .build();
        for (UserEntity user : courseEntity.getUsers()) {
            user.setCourseEntity(courseEntity);
            assertEquals(courseEntity, user.getCourseEntity());
        }
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        verify(courseRepository).save(any(CourseEntity.class));
        assertEquals(response.getId(), courseEntity.getId());

    }
}