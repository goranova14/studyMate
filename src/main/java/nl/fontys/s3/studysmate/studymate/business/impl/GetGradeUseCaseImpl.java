package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Grade;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetGradeUseCaseImpl implements GetGradeUseCase {
    private GradeRepository gradeRepository;


    @Override
    public Grade getGrade(long assignmentId, long studentPcn) {
        Optional<GradeEntity> optionalGrade = gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentId, studentPcn);
        if (optionalGrade.isEmpty()) {
            throw new InvalidEntityException("No grade");
        } else {
           return Grade.builder().gradeNum(optionalGrade.get().getGrade()).userPcn(optionalGrade.get().getUserEntity().getPcn()).assignmentId(optionalGrade.get().getAssignmentEntity().getId())
                    .assignmentUrl(optionalGrade.get().getAssignmentUrl()).id(optionalGrade.get().getId()).submissionDate(optionalGrade.get().getSubmissionDate())
                    .build();

        }
    }
}
