package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentControllerTest {
    @Mock//Mockito-basically create a mock object of the class
    private CreateAssignmentUseCase createAssignmentUseCase;
    @Mock
    private GetAssignmentsDeadlineDESCUseCase getAssignmentsDeadlineDESCUseCase;
    @Mock
    private GetAssignmentsDeadlineASCUseCase getAssignmentsDeadlineASCUseCase;
    @InjectMocks//injects the mock object into the class
    private AssignmentController assignmentController;
    @Mock//injects the mock object into the class
    private CourseRepository courseRepository;
    @Mock
    private UpdateAssignmentUseCase updateAssignmentUseCase;
    @Mock
    private GetAssignmentsUseCase getAssignmentsUseCase;
    @Mock
    private DeleteAssignmentUseCase deleteAssignmentUseCase;
    @Mock
    private UploadAssignmentUseCase uploadAssignmentUseCase;
    @Mock
    private GetUploadedAssignmentUseCase getUploadedAssignmentUseCase;

    @Test
    void uploadAssigmentUrl(){
        MockMultipartFile assignmentFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test".getBytes());

        ResponseEntity<Object> response=assignmentController.uploadAssignment(assignmentFile,2l,3l);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    void getUploadedAssignmentPositive(){
        ResponseEntity<Object> actual=assignmentController.getUploadedAssignment(1l,2l);
        assertEquals(HttpStatus.OK,actual.getStatusCode());
    }
//    @Test
//    void getUploadedAssignmentNegative(){
//       when(getUploadedAssignmentUseCase.getUrl(1l,2l)).thenThrow(InvalidEntityException.class);
//        ResponseEntity<Object> actual=assignmentController.getUploadedAssignment(1l,2l);
//        assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());
//        verify(getUploadedAssignmentUseCase).getUrl(1l,2l);
//    }
//    @Test
//    void uploadAssigmentUrlNegative() throws  InvalidEntityException{
//        MockMultipartFile assignmentFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test".getBytes());
//
//        doThrow(new InvalidEntityException("s")).when(uploadAssignmentUseCase).uploadAssignment(assignmentFile,2l,3l);
//        ResponseEntity<Object> response = assignmentController.uploadAssignment(assignmentFile, 2l, 3l);
//
//        verify(uploadAssignmentUseCase).uploadAssignment(assignmentFile, 2l, 3l);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

    @Test
    void createAssignment() {
        CourseEntity entity = CourseEntity.builder()
                .id(1L)
                .build();

        CreateAssignmentRequest request = CreateAssignmentRequest.builder()
                .courseEntity(entity)
                .build();
        CreateAssignmentResponse response = CreateAssignmentResponse.builder()
                .build();
        when(courseRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(createAssignmentUseCase.createAssignment(request)).thenReturn(response);

        ResponseEntity<CreateAssignmentResponse> actualResponse = assignmentController.createAssignment(1L, request);

        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        verify(courseRepository).findById(entity.getId());
        verify(createAssignmentUseCase).createAssignment(request);

    }

    @Test
    void createAssignmentNegative() {
        CourseEntity entity = CourseEntity.builder()
                .id(1L)
                .build();
        CreateAssignmentRequest request = CreateAssignmentRequest.builder()
                .courseEntity(entity)
                .build();
        when(courseRepository.findById(entity.getId())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> assignmentController.createAssignment(1L, request));
        verify(courseRepository).findById(entity.getId());

    }

    @Test
    void updateAssignmentPositive() {
        UpdateAssignmentRequest request = UpdateAssignmentRequest.builder()
                .description("Introduction")
                .name("ICT")
                .build();
        ResponseEntity<Response> response = assignmentController.updateAssignment(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

//    @Test
//    void UpdateAssignmentNegative() {
//        UpdateAssignmentRequest request = UpdateAssignmentRequest.builder()
//                .description("Introduction")
//                .name("ICT")
//                .build();
//        doThrow(new InvalidEntityException("Not found")).when(updateAssignmentUseCase).updateAssignment(request);
//        ResponseEntity<Response> response = assignmentController.updateAssignment(1L, request);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(updateAssignmentUseCase).updateAssignment(request);
//    }

    @Test
    void deleteAssignment() {
        doNothing().when(deleteAssignmentUseCase).deleteAssignment(1L);
        ResponseEntity<Void> response=assignmentController.deleteAssignment(1L);

        assertEquals(HttpStatus.OK,response.getStatusCode());


    }

    @Test
    void getAllAssignmentsForCourse() {
        GetAllAssignmentsRequest request=GetAllAssignmentsRequest.builder()
                .courseId(1L)
                .build();
        GetAllAssignmentsResponse response=GetAllAssignmentsResponse.builder()

                .build();
        when(getAssignmentsUseCase.getAssignments(request)).thenReturn(response);
        ResponseEntity<GetAllAssignmentsResponse> actualResponse=assignmentController.getAllAssignmentsForCourse(1L);
        assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
        verify(getAssignmentsUseCase).getAssignments(request);
    }
    @Test
    void getAllAssignmentsForCourseASC() {
        GetAllAssignmentsRequest request=GetAllAssignmentsRequest.builder()
                .courseId(1L)
                .build();
        GetAllAssignmentsResponse response=GetAllAssignmentsResponse.builder()
                .build();
        when(getAssignmentsDeadlineASCUseCase.getAssignments(request)).thenReturn(response);
        ResponseEntity<GetAllAssignmentsResponse> actualResponse=assignmentController.getAllAssignmentsForCourseDeadlineASC(1L);
        assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
        verify(getAssignmentsDeadlineASCUseCase).getAssignments(request);
    }@Test
    void getAllAssignmentsForCourseDESC() {
        GetAllAssignmentsRequest request=GetAllAssignmentsRequest.builder()
                .courseId(1L)
                .build();
        GetAllAssignmentsResponse response=GetAllAssignmentsResponse.builder()
                .build();
        when(getAssignmentsDeadlineDESCUseCase.getAssignments(request)).thenReturn(response);
        ResponseEntity<GetAllAssignmentsResponse> actualResponse=assignmentController.getAllAssignmentsForCourseDeadlineDESC(1L);
        assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
        verify(getAssignmentsDeadlineDESCUseCase).getAssignments(request);
    }
}