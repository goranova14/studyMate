package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetAvgGradesForStudentUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAvgGradesForUserUseCaseImpl implements GetAvgGradesForStudentUseCase {

    private GradeRepository gradeRepository;
    private UserRepository userRepository;

    @Override
    public GetAvgGradesResponse getAvgGrade(Long id) {

        Optional<UserEntity> foundUser = userRepository.findByPcn(id);
        if (foundUser.isEmpty()) {
            throw new InvalidEntityException("No user found");
        } else {
            try {
                double averageGrade = gradeRepository.getAverageGradeForUser(foundUser.get().getPcn());
                return GetAvgGradesResponse.builder().grade(averageGrade).build();
            } catch (NullPointerException nullPointerException) {
                throw new InvalidEntityException("No  found");

            }

        }
    }
}
