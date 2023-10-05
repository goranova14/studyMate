package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetStudentsUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GetStudentsUseCaseImpl implements GetStudentsUseCase {

    private UserRepository userRepository;

    @Override
    public GetAllStudentsResponse getStudents(final GetAllStudentsRequest request) {

        List<UserEntity> students = userRepository.findAll();
        final GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<User> results = students
                .stream()
                .map(UserConverter::convert)
                .toList();
        response.setUsers(results);
        if (results.isEmpty()) {
            throw new InvalidEntityException("No students found");
        } else {
            return response;
        }

    }

}
