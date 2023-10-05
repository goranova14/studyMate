package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UpdateAssignmentUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.UpdateAssignmentRequest;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateAssignmentUseCaseImpl implements UpdateAssignmentUseCase {
  private final AssignmentRepository assignmentRepository;

    @Override
    public void updateAssignment(UpdateAssignmentRequest request) {
        Optional<AssignmentEntity> assignment=assignmentRepository.findById(request.getId());
        if (assignment.isPresent()){
            AssignmentEntity newAssignment=assignment.get();
            newAssignment.setDescription(request.getDescription());
            newAssignment.setDeadline(request.getDeadline());
            newAssignment.setName(request.getName());
            assignmentRepository.save(newAssignment);
        }
        else {
            throw  new InvalidEntityException("INVALID_ID");
        }
    }
}
