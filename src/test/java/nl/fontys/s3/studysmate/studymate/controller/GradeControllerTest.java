package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.business.CreateGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.DeleteGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.UpdateGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeControllerTest {
    @Mock
    private CreateGradeUseCase createGradeUseCase;
    @Mock
    private UpdateGradeUseCase updateGradeUseCase;
    @Mock
    private DeleteGradeUseCase deleteGradeUseCase;
    @Mock
    private GetGradeUseCase getGradeUseCase;
    @InjectMocks
    private GradeController gradeController;
    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void createGrade() {
        AssignmentEntity assignment = AssignmentEntity.builder().id(1l).build();
        UserEntity user = UserEntity.builder().pcn(1l).build();
        CreateGradeRequest request = CreateGradeRequest.builder().user(user).assignment(assignment).build();
        CreateGradeResponse response = CreateGradeResponse.builder().build();
//        when(assignmentRepository.findById(assignment.getId())).thenReturn(Optional.of(assignment));
//        when(userRepository.findByPcn(user.getPcn())).thenReturn(Optional.of(user));
        when(createGradeUseCase.createGrade(request)).thenReturn(response);
        ResponseEntity<CreateGradeResponse> responseResponseEntity = gradeController.createGrade(request);
        assertEquals(HttpStatus.CREATED, responseResponseEntity.getStatusCode());
    }

//    @Test
//    void createGradeReturnsNotFound() {
//        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
//        UserEntity userEntity = UserEntity.builder().pcn(1l).build();
//        CreateGradeRequest request = CreateGradeRequest.builder().assignment(assignmentEntity).user(userEntity).build();
//        when(assignmentRepository.findById(assignmentEntity.getId())).thenReturn(Optional.of(assignmentEntity));
//
//        when(userRepository.findByPcn(request.getUser().getPcn())).thenReturn(Optional.empty());
//        ResponseEntity<CreateGradeResponse> response = gradeController.createGrade(request);
//        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//        verify(assignmentRepository).findById(assignmentEntity.getId());
//        verify(userRepository).findByPcn(request.getUser().getPcn());
//    }

//    @Test
//    void createGradeNegative() {
//        UserEntity user = UserEntity.builder().pcn(1l).build();
//        AssignmentEntity assignment = AssignmentEntity.builder().id(1l).build();
//
//        CreateGradeRequest request = CreateGradeRequest.builder().user(user).assignment(assignment).build();
//        ResponseEntity<CreateGradeResponse> response = gradeController.createGrade(request);
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

    @Test
    void updateGrade() {
        AssignmentEntity assignment = AssignmentEntity.builder().id(1l).build();
        UserEntity user = UserEntity.builder().pcn(1l).build();
        UpdateGradeRequest request = UpdateGradeRequest.builder().gradeNum(1).build();
        ResponseEntity<Response> response = gradeController.updateGrade( request);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }



    @Test
    void getGradePositive() {
        Grade grade = Grade.builder()
                .gradeNum(1)
                .build();
        when(getGradeUseCase.getGrade(1l, 2l)).thenReturn(grade);

        ResponseEntity<Object> response = gradeController.getGradeFromStudentPcnandAssginmentId(1l, 2l);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(getGradeUseCase).getGrade(1l, 2l);
    }

//    @Test
//    void getGradeNegative(){
//      when(getGradeUseCase.getGrade(1l,2l)).thenThrow(new InvalidEntityException("a"));
//      ResponseEntity<Object> response=gradeController.getGradeFromStudentPcnandAssginmentId(1l,2l);
//      assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//
//        verify(getGradeUseCase).getGrade(1l, 2l);
//
//    }
    @Test
    void deleteGradeShouldReturnOk() {
        doNothing().when(deleteGradeUseCase).deleteGrade(1l,2l);
        ResponseEntity<Response> response = gradeController.deleteGrade(1l,2l);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @Test
//    void updateStudentShouldThrowException() {
//        GradeEntity grade = GradeEntity.builder()
//                .grade(1)
//                .build();
//        UpdateGradeRequest request = UpdateGradeRequest.builder()
//                .gradeNum(1)
//                .build();
//        InvalidEntityException exceptionInvalidUser = new InvalidEntityException("No grade found");
//        doThrow(exceptionInvalidUser).when(updateGradeUseCase).updateGrade(request);
//        ResponseEntity<Response> response = gradeController.updateGrade(request);
//
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(updateGradeUseCase).updateGrade(request);
//
//    }

    @Test
    void updateGradeShouldReturnOk() {
        GradeEntity grade = GradeEntity.builder()
                .grade(1)
                .build();
        UpdateGradeRequest request = UpdateGradeRequest.builder()
                .gradeNum(1)
                .build();
        doNothing().when(updateGradeUseCase).updateGrade(request);
        ResponseEntity<Response> response = gradeController.updateGrade( request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(updateGradeUseCase).updateGrade(request);

    }
}