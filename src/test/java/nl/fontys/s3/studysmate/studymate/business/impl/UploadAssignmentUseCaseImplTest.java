package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CloudinaryRepo;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadAssignmentUseCaseImplTest {
    @InjectMocks
    private UploadAssignmentUseCaseImpl uploadAssignmentUseCase;
    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private CloudinaryRepo cloudinaryRepo;
    @Test
    void uploadAssignment() {
        MultipartFile assignmentFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();

        when(userRepository.findByPcn(userEntity.getPcn())).thenReturn(Optional.of(userEntity));
        when(assignmentRepository.findById(assignmentEntity.getId())).thenReturn(Optional.of(assignmentEntity));
        String expectedUrl = "http://1221333.com/file";
        when(cloudinaryRepo.uploadAssignment(assignmentFile)).thenReturn(expectedUrl);
        uploadAssignmentUseCase.uploadAssignment(assignmentFile, userEntity.getPcn(), assignmentEntity.getId());

        verify(userRepository).findByPcn(userEntity.getPcn());
        verify(assignmentRepository).findById(assignmentEntity.getId());
    }@Test
    void uploadAssignmentNegative() {
        MultipartFile assignmentFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();

        when(assignmentRepository.findById(assignmentEntity.getId())).thenReturn(Optional.empty());
        when(userRepository.findByPcn(userEntity.getPcn())).thenReturn(Optional.empty());

        assertThrows(InvalidEntityException.class, () -> uploadAssignmentUseCase.uploadAssignment(assignmentFile, userEntity.getPcn(), assignmentEntity.getId()));

    }

    @Test
    void uploadAssignmentHasAlreadyBeenSubmitted(){
        MultipartFile assignmentFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        GradeEntity grade= GradeEntity.builder().grade(1).id(1l).assignmentUrl("test").assignmentEntity(assignmentEntity).userEntity(userEntity).build();
        when(userRepository.findByPcn(userEntity.getPcn())).thenReturn(Optional.of(userEntity));
        when(assignmentRepository.findById(assignmentEntity.getId())).thenReturn(Optional.of(assignmentEntity));
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn())).thenReturn(Optional.of(grade));
        assertThrows(InvalidEntityException.class, () -> uploadAssignmentUseCase.uploadAssignment(assignmentFile, userEntity.getPcn(), assignmentEntity.getId()));

        verify(userRepository).findByPcn(userEntity.getPcn());
        verify(assignmentRepository).findById(assignmentEntity.getId());
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn());
    }
}