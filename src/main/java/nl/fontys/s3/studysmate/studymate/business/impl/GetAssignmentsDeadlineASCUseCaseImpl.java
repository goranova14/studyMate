package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetAssignmentsDeadlineASCUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Assignment;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAssignmentsDeadlineASCUseCaseImpl implements GetAssignmentsDeadlineASCUseCase {
    private AssignmentRepository assignmentsRepository;

    private CourseRepository courseRepository;


    @Override
    public GetAllAssignmentsResponse getAssignments(GetAllAssignmentsRequest request) {
        List<AssignmentEntity> assignmentEntities = assignmentsRepository.findAllByCourseEntity_IdDeadlineASC(request.getCourseId());
        final  GetAllAssignmentsResponse response=new GetAllAssignmentsResponse();
        List<Assignment> results=assignmentEntities.stream()
                .map(AssignmentConverter::convert)
                .toList();
        response.setAssignmentList(results);
        if (results.isEmpty()){
            throw new InvalidEntityException("No assignments found");

        }
        else {
            return  response;
        }
    }
}
