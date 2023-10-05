package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UploadAssignmentUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.persistence.*;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UploadAssignmentUseCaseImpl implements UploadAssignmentUseCase {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final AssignmentRepository assignmentRepository;
    private final CloudinaryRepo cloudinaryRepo;

    public void uploadAssignment(MultipartFile assignmentFile, Long studentPcn, Long assignmentId) {
        Optional<UserEntity> user = userRepository.findByPcn(studentPcn);
        Optional<AssignmentEntity> assignment = assignmentRepository.findById(assignmentId);
        if (user.isEmpty() || assignment.isEmpty()) {
            throw new InvalidEntityException("User or assignment not found");
        } else {
            Optional<GradeEntity> foundGrade = gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentId, studentPcn);

            if (foundGrade.isEmpty()) {
                if(assignmentFile==null){
                    throw new InvalidEntityException("File is empty");

                }
                AssignmentEntity updatedAssignment = assignment.get();
                UserEntity userEntity = user.get();
                String url = cloudinaryRepo.uploadAssignment(assignmentFile);
                LocalDateTime submissionDate=LocalDateTime.now();
                GradeEntity grade = GradeEntity.builder().grade(0).assignmentUrl(url).submissionDate(submissionDate).assignmentEntity(updatedAssignment).userEntity(userEntity).build();
                gradeRepository.save(grade);
            } else {
                throw new InvalidEntityException("User has already submitted assignment");

            }
        }
    }


}
