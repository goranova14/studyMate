package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetUploadedAssignmentUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetUploadedAssignmentResponse;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUploadedAssignmentUseCaseImpl implements GetUploadedAssignmentUseCase {
    private GradeRepository gradeRepository;


    @Override
    public GetUploadedAssignmentResponse getUrl(Long assignmentId,Long studetnPcn) {
        Optional<GradeEntity> foundGrade = gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentId, studetnPcn);
        if (foundGrade.isPresent() && !foundGrade.get().getAssignmentUrl().isEmpty()) {
            return GetUploadedAssignmentResponse.builder().url(foundGrade.get().getAssignmentUrl()).build();
        }
        throw  new InvalidEntityException("No submission found");

    }
}
