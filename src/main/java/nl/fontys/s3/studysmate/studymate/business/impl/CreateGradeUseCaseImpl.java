package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.CreateGradeRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateGradeResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateGradeUseCaseImpl implements CreateGradeUseCase {
    private final GradeRepository gradeRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;


    public CreateGradeResponse createGrade(CreateGradeRequest request) {
        AssignmentEntity assignment = assignmentRepository.findById(request.getAssignment().getId())
                .orElseThrow(() -> new InvalidEntityException("Assignment not found"));
        request.setAssignment(assignment);
        Optional<UserEntity> user = userRepository.findByPcn(request.getUser().getPcn());
        if (!user.isPresent()) {
            throw new InvalidEntityException("User not found");
        }
        Optional<GradeEntity> gradeEntity = gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(request.getAssignment().getId(), request.getUser().getPcn());
        if (gradeEntity.isPresent()) {
            if (gradeEntity.get().getGrade() != 0) {
                throw new InvalidEntityException("Already graded");

            } else {
                GradeEntity grade=gradeEntity.get();
                grade.setGrade(request.getGradeNum());
                        grade.setAssignmentEntity(gradeEntity.get().getAssignmentEntity());
                        grade.setUserEntity(gradeEntity.get().getUserEntity());
                        grade.setAssignmentUrl(gradeEntity.get().getAssignmentUrl());
                gradeRepository.save(grade);
                return CreateGradeResponse.builder()
                        .grade(grade.getGrade())
                        .build();
            }
        }
        else{
            throw new InvalidEntityException("User hasn't submitted his work");

        }


    }
}
