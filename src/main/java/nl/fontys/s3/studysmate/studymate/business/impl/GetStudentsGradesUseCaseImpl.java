package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetStudentsGradesUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetStudentsGradesUseCaseImpl implements GetStudentsGradesUseCase {

    private UserRepository userRepository;
    private GradeRepository gradeRepository;

    @Override
    public GetAllGradesForUserResponse getStudents(final int requestNum) {

        List<GradeEntity> gradeEntities = gradeRepository.getGradeByUserEntityPcn(requestNum);


        final GetAllGradesForUserResponse response = new GetAllGradesForUserResponse();
        List<Grade> results = gradeEntities
                .stream()
                .map(GradeConverter::convert)
                .toList();
        response.setGrades(results);
        if (results.isEmpty()) {
            throw new InvalidEntityException("No grades found");
        } else {
            return response;
        }

    }

}
