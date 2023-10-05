package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UpdateGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.UpdateGradeRequest;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateGradeUseCaseImpl implements UpdateGradeUseCase {
  private final GradeRepository gradeRepository;



    @Override
    public void updateGrade(UpdateGradeRequest request) {
        Optional<GradeEntity> optionalGrade=gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(request.getAssignment().getId(),request.getUser().getPcn());
        if (optionalGrade.isPresent()){
            GradeEntity newGrade=optionalGrade.get();
            newGrade.setAssignmentEntity(request.getAssignment());
            newGrade.setGrade(request.getGradeNum());
            newGrade.setUserEntity(request.getUser());
            gradeRepository.save(newGrade);
        }
        else{
            throw  new InvalidEntityException("INVALID_ID");
        }
    }
}
