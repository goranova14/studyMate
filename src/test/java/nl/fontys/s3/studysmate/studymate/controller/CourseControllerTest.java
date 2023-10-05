package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)//allows to include the Mockito framework
class CourseControllerTest {
    @Mock//Mockito-basically create a mock object of the class
    private CreateCourseUseCase createCourseUseCase;
    @InjectMocks//injects the mock object into the class
    private CourseController courseController;
    @Mock
    private UpdateCourseUseCase updateCourseUseCase;
    @Mock
    private GetCoursesUseCase getCoursesUseCase;
    @Mock
    private GetCourseUseCase getCourseUseCase;

    @Test
    void getCourseId(){
        Course course = Course.builder()
                .name("11")
                .build();
        when(getCourseUseCase.getCourse(11)).thenReturn(Optional.of(course));

        ResponseEntity<Course> response=courseController.getCourseId(11);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(getCourseUseCase).getCourse(11);
    }
//    @Test
//    void getCourseIdNegative(){
//
//        when(getCourseUseCase.getCourse(11)).thenReturn(Optional.empty());
//
//        ResponseEntity<Course> response=courseController.getCourseId(11);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        verify(getCourseUseCase).getCourse(11);
//    }
//    @Test
//    void getCourseUsersNegative(){
//
//        when(getCourseUseCase.getCourse(11)).thenReturn(Optional.empty());
//
//        ResponseEntity<GetAllStudentsResponse> response=courseController.getUsersForCourse(11);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        verify(getCourseUseCase).getCourse(11);
//    }
    @Test
    void getAllPositive() {
        List<Course> courses = new ArrayList<>();
        Course course = Course.builder()
                .name("11")
                .build();
        Course course1 = Course.builder()
                .name("12")
                .build();
        courses.add(course);
        courses.add(course1);

        GetAllCoursesResponse response = GetAllCoursesResponse.builder().courses(courses).build();

        GetAllCoursesRequest request = GetAllCoursesRequest.builder().build();
        when(getCoursesUseCase.getCourses(request)).thenReturn(response);

        ResponseEntity<GetAllCoursesResponse> responseActual = courseController.getAllCourses();
        verify(getCoursesUseCase).getCourses(request);
        assertEquals(HttpStatus.OK, responseActual.getStatusCode());

    }   @Test
    void getAllUsersForCoursePositive() {

        List<User> users = new ArrayList<>();
        User user2 = User.builder()
                .pcn(2l)
                .build();
        User user1 = User.builder()
                .pcn(1l)
                .build();
        users.add(user1);
        users.add(user2);

        Course course= Course.builder()
                .name("12")
                .id(1l)
                .users(users)
                .build();
        when(getCourseUseCase.getCourse(course.getId())).thenReturn(Optional.of(course));



        ResponseEntity<GetAllStudentsResponse> responseActual = courseController.getUsersForCourse(course.getId());
        verify(getCourseUseCase).getCourse(course.getId());
        assertEquals(HttpStatus.OK, responseActual.getStatusCode());
    }

    @Test
    void createCourse() {
        CreateCourseRequest request = CreateCourseRequest.builder()
                .name("Introduction to Java")
                .description("Learn the basics of Java programming language")
                .build();
        CreateCourseResponse expectedResponse = CreateCourseResponse.builder()
                .id(12345)
                .title("Introduction to Java")
                .build();

        when(createCourseUseCase.createCourse(request)).thenReturn(expectedResponse);
        ResponseEntity<CreateCourseResponse> response = courseController.createCourse(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void UpdateCoursePositive() {
        UpdateCourseRequest request = UpdateCourseRequest.builder()
                .name("er")
                .description("de")
                .id(1L)
                .build();

        ResponseEntity<Response> response = courseController.updateCourse(1, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

//    @Test
//    void updateCourseNegative() {
//        UpdateCourseRequest request = UpdateCourseRequest.builder()
//                .name("er")
//                .description("de")
//                .id(1L)
//                .build();
//        doThrow(new InvalidEntityException("Invalid course")).when(updateCourseUseCase).updateCourse(request);
//        ResponseEntity<Response> response = courseController.updateCourse(1, request);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//    }
}