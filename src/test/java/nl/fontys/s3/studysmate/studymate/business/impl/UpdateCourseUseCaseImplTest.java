package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.UpdateCourseRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCourseUseCaseImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateCourseUseCaseImpl updateCourseUseCase;

    @Test
    void updateCourse() {
        List<UserEntity> users = new ArrayList<>();

        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .name("course")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .email("abc@bg.com")
                .build();

        users.add(userEntity);
        course.setUsers(users);

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .id(1L)
                .users(new ArrayList<>())
                .build();
        when(courseRepository.findById(request.getId())).thenReturn(Optional.of(course));
        when(userRepository.findByEmail("abc@bg.com")).thenReturn(Optional.of(userEntity));
        updateCourseUseCase.updateCourse(request);

        verify(courseRepository).findById(request.getId());
        verify(userRepository).findByEmail("abc@bg.com");
        assertEquals(request.getId(), course.getId());
    }

    @Test
    void updateWithListOfUsers() {
        List<UserEntity> users = new ArrayList<>();

        CourseEntity course = CourseEntity.builder()
                .id(1L)
                .name("course")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .email("abc@bg.com")
                .build();
        UserEntity userEntity1 = UserEntity.builder()
                .email("abc123@bg.com")
                .build();
        users.add(userEntity);
        users.add(userEntity1);
        course.setUsers(users);
        List<UserEntity> usersToRemove = new ArrayList<>();
        usersToRemove.add(userEntity);
        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .id(1l)
                .users(usersToRemove)
                .build();
        when(courseRepository.findById(request.getId())).thenReturn(Optional.of(course));
        when(userRepository.findByEmail("abc@bg.com")).thenReturn(Optional.of(userEntity));
        when(userRepository.findByEmail("abc123@bg.com")).thenReturn(Optional.of(userEntity));

        updateCourseUseCase.updateCourse(request);
        assertEquals(course.getUsers(), request.getUsers());

        verify(courseRepository).findById(request.getId());
        verify(userRepository).findByEmail("abc123@bg.com");
        verify(userRepository).findByEmail("abc@bg.com");

    }

    @Test
    void updateCourseNegative() {

        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .id(1L)
                .build();
        when(courseRepository.findById(request.getId())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> updateCourseUseCase.updateCourse(request));
        verify(courseRepository).findById(request.getId());
    }
}