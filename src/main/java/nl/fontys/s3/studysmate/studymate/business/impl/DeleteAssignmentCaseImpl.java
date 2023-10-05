package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.DeleteAssignmentUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAssignmentCaseImpl implements DeleteAssignmentUseCase {

    private final AssignmentRepository assignmentRepository;

    @Override
    public void deleteAssignment(long id) {

        boolean exists = assignmentRepository.existsById(id);
        if (!exists) {
            throw new InvalidEntityException("No assignment found with id:" + id);

        } else {
            this.assignmentRepository.deleteById(id);

        }
    }
}
