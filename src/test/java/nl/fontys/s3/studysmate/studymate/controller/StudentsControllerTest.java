package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.impl.GetAvgGradesForUserUseCaseImpl;
import nl.fontys.s3.studysmate.studymate.business.impl.GetGradesForUserUseCaseImpl;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)//allows to include the Mockito framework
class StudentsControllerTest {
    @Mock//Mockito-basically create a mock object of the class
    private CreateUserUseCase createUserUseCase;
    @InjectMocks//injects the mock object into the class
    private UserController studentsController;
    @Mock//Mockito-basically create a mock object of the class
    private GetStudentsUseCase getStudentsUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private GetStudentPCNUseCase getStudentPCNUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private DeleteUserUseCase deleteUserUseCase;
    @Mock
    private GetAvgGradesForUserUseCaseImpl getAvgGradesForUserUseCase;
    @Mock
    private GetGradesForUserUseCaseImpl getGradesForUserUseCase;

    @Mock//Mockito-basically create a mock object of the class
    private UpdateUserUseCase updateUserUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private LoginUseCase loginUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private GetUserEmailUseCase getUserEmailUseCase;

    @Test
    void createStudentShouldReturnResponse() {


        CreateStudentRequest request = CreateStudentRequest.builder().firstName("Denitsa").lastName("Goranova").address("Niuewe").password("Bulgaria").email("de").pcn(1L).build();
        CreateStudentResponse expectedResponse = CreateStudentResponse.builder().pcn(1L).build();
        when(createUserUseCase.createStudent(request)).thenReturn(expectedResponse);
        ResponseEntity<Object> response = studentsController.createStudent(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());//201Created
        assertEquals(expectedResponse, response.getBody());
        verify(createUserUseCase).createStudent(request);
    }

//    @Test
//    void createStudentShouldThrowAPCNAlreadyExistsException() {
//        CreateStudentRequest request = CreateStudentRequest.builder().pcn(1L)
//                .lastName("Goranova")
//                .firstName("Denitsa")
//                .password("Netherlands")
//                .address("Niuewe")
//                .email("sa")
//                .build();
//
//        when(studentsController.createStudent(request)).thenThrow(PcnAlreadyExistsException.class);
//        assertThrows(PcnAlreadyExistsException.class, () -> createUserUseCase.createStudent(request));
//    }

    @Test
    void getAllStudentsShouldThrowANInvalidStudentException() {
        GetAllStudentsRequest request = GetAllStudentsRequest
                .builder()
                .build();
        when(getStudentsUseCase.getStudents(request)).thenThrow(InvalidEntityException.class);

        assertThrows(InvalidEntityException.class, () -> studentsController.getAllStudents());

        verify(getStudentsUseCase).getStudents(request);

    }

    @Test
    void getAllStudentsShouldReturnListOfStudents() {

        User user1 = User.builder().firstName("Denitsa").lastName("Goranova").address("Street1").password("Bulgaria").pcn(1L).build();
        User user2 = User.builder().firstName("Mirela").lastName("Goranova").address("Street1").password("Bulgaria").pcn(2L).build();

        GetAllStudentsRequest request = GetAllStudentsRequest.builder().build();

        GetAllStudentsResponse expectedResult = GetAllStudentsResponse.builder().users(List.of(user2, user1)).build();

        when(getStudentsUseCase.getStudents(request)).thenReturn(expectedResult);
        ResponseEntity<GetAllStudentsResponse> response = studentsController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());//201Created
        assertEquals(expectedResult.getUsers(), response.getBody().getUsers());
        verify(getStudentsUseCase).getStudents(request);

    }


    @Test
    void getStudentShouldReturnStudentObject() {
        User user1 = User.builder().firstName("Denitsa").lastName("Goranova").address("Street1").password("Bulgaria").pcn(1L).build();

        when(getStudentPCNUseCase.getStudent(1)).thenReturn(Optional.of(user1));
        ResponseEntity<User> response = studentsController.getStudentPCN(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());//201Created
        assertEquals(user1, response.getBody());
        verify(getStudentPCNUseCase).getStudent(1);

    }

//    @Test
//    void getStudentShouldReturnNotFound() {
//
//        when(getStudentPCNUseCase.getStudent(1)).thenReturn(Optional.empty());
//        ResponseEntity<User> response = studentsController.getStudentPCN(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());//201Created
//        verify(getStudentPCNUseCase).getStudent(1);
//
//    }

    @Test
    void getStudentShouldThrowAnInvalidStudentException() {
        long pcn = 1L;

        when(getStudentPCNUseCase.getStudent(pcn)).thenThrow(InvalidEntityException.class);

        assertThrows(InvalidEntityException.class, () -> studentsController.getStudentPCN(pcn));

        verify(getStudentPCNUseCase).getStudent(pcn);
    }

    @Test
    void deleteStudentShouldReturnNoContent() {
        long pcn = 1L;
        doNothing().when(deleteUserUseCase).deleteStudent(pcn);
        ResponseEntity<Response> response = studentsController.deleteStudent(pcn);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void updateStudentShouldReturnNoContent() {
        User user = User.builder().firstName("Denitsa").lastName("Goranova").address("Street1").password("Bulgaria").pcn(1L).build();
        UpdateStudentRequest request = UpdateStudentRequest.builder()
                .firstName("Deni")
                .lastName("Goranova")
                .password("Netherlands")
                .address("Street Lovely 12")
                .build();
        doNothing().when(updateUserUseCase).updateStudent(request);
        ResponseEntity<Response> response = studentsController.updateStudent(user.getPcn(), request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(updateUserUseCase).updateStudent(request);

    }

//    @Test
//    void getAvgGradeForUserNegative() {
//        InvalidEntityException exceptionInvalidUser = new InvalidEntityException("No user found");
//        when(getAvgGradesForUserUseCase.getAvgGrade(1l)).thenThrow(exceptionInvalidUser);
//        ResponseEntity<Object> response = studentsController.getAvgGradeForUser(1l);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(getAvgGradesForUserUseCase).getAvgGrade(1l);
//    }

    @Test
    void getAvgGradeForUserPositive() {
        GetAvgGradesResponse response = GetAvgGradesResponse.builder().grade(2.5).build();
        when(getAvgGradesForUserUseCase.getAvgGrade(1l)).thenReturn(response);
        ResponseEntity<Object> actualResponse = studentsController.getAvgGradeForUser(1l);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        verify(getAvgGradesForUserUseCase).getAvgGrade(1l);
    }

//    @Test
//    void updateStudentShouldThrowException() {
//        User user = User.builder().firstName("Denitsa").lastName("Goranova").address("Street1").password("Bulgaria").pcn(1L).build();
//        UpdateStudentRequest request = UpdateStudentRequest.builder()
//                .firstName("Deni")
//                .lastName("Goranova")
//                .password("Netherlands")
//                .address("Street Lovely 12")
//                .build();
//        InvalidEntityException exceptionInvalidUser = new InvalidEntityException("No user found");
//        doThrow(exceptionInvalidUser).when(updateUserUseCase).updateStudent(request);
//        ResponseEntity<String> response = studentsController.updateStudent(user.getPcn(), request);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(updateUserUseCase).updateStudent(request);
//
//    }

    @Test
    void getGrades() {
        Grade grade1 = Grade.builder().gradeNum(1).id(1l).build();
        Grade grade2 = Grade.builder().gradeNum(9).id(2l).build();
        GetAllGradesForUserResponse response = GetAllGradesForUserResponse.builder().grades(List.of(grade1, grade2)).build();
        when(getGradesForUserUseCase.getGrades(1l)).thenReturn(response);
        ResponseEntity<GetAllGradesForUserResponse> actualResponse = studentsController.getAllGrades(1l);
        assertEquals(response, actualResponse.getBody());
        verify(getGradesForUserUseCase).getGrades(1l);

    }

    @Test
    void loginPositive() {

        LoginRequest request = LoginRequest.builder()
                .password("as")
                .email("a@abv.bg")
                .build();

        LoginResponse response = LoginResponse.builder().accessToken("token").build();
        when(loginUseCase.login(request)).thenReturn(response);
        ResponseEntity<LoginResponse> responseResponseEntity = studentsController.login(request);

        assertEquals(response, responseResponseEntity.getBody());

    }

    @Test
    void LoginNegative() {
        LoginRequest request = LoginRequest.builder()
                .password("as")
                .email("a@abv.bg")
                .build();

        when(loginUseCase.login(request)).thenThrow(InvalidEntityException.class);
//        ResponseEntity<Object> responseResponseEntity = studentsController.login(request);
//        assertEquals(HttpStatus.BAD_REQUEST, responseResponseEntity.getStatusCode());
        assertThrows(InvalidEntityException.class, () -> studentsController.login(request));
        verify(loginUseCase).login(request);

    }
}
