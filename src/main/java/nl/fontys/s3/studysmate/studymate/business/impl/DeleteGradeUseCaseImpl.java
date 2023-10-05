package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.DeleteGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteGradeUseCaseImpl implements DeleteGradeUseCase {

    private final GradeRepository gradeRepository;


    @Override
    public void deleteGrade(long assignmentId,long studentPcn) {
        Optional<GradeEntity> optionalGrade=gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentId,studentPcn);

        if (!optionalGrade.isPresent()) {
            throw new InvalidEntityException("No grade found"  );

        } else {
            this.gradeRepository.deleteById(optionalGrade.get().getId());

        }
    }
}
