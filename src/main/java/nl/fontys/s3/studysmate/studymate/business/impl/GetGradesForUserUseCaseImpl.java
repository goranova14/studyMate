package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetGradesForUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetGradesForUserUseCaseImpl implements GetGradesForUserUseCase {

    private GradeRepository gradeRepository;

    @Override
    public GetAllGradesForUserResponse getGrades(Long studentPcn) {
        List<GradeEntity> gradeEntities=gradeRepository.findAllByUserEntityPcn(studentPcn);
        final GetAllGradesForUserResponse response=new GetAllGradesForUserResponse();
        List<Grade> results=gradeEntities.stream().map(GradeConverter::convert).toList();
        if (results.isEmpty()){
            throw new InvalidEntityException("No grades found");

        }
        else {
            response.setGrades(results);

            return  response;
        }
    }
}
