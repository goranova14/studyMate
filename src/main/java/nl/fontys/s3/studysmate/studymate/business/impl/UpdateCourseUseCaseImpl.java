package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UpdateCourseUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.UpdateCourseRequest;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCourseUseCaseImpl implements UpdateCourseUseCase {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public void updateCourse(UpdateCourseRequest request) {
        Optional<CourseEntity> courseOpt = courseRepository.findById(request.getId());
        if (courseOpt.isPresent()) {
            CourseEntity course = courseOpt.get();
            course.setName(request.getName());
            course.setDescription(request.getDescription());
            List<UserEntity> updatedUsers = new ArrayList<>();
            for (UserEntity userEntity:course.getUsers()){
                if (!request.getUsers().contains(userEntity)){
                    UserEntity updatedUser = userRepository.findByEmail(userEntity.getEmail())
                            .orElseThrow(() -> new InvalidEntityException("INVALID_USER"));
                        updatedUser.setCourseEntity(null);
                userRepository.save(updatedUser);
                }
            }




            for (UserEntity user : request.getUsers()) {
                UserEntity updatedUser = userRepository.findByEmail(user.getEmail())
                        .orElseThrow(() -> new InvalidEntityException("INVALID_USER"));
                updatedUser.setCourseEntity(course);
                updatedUsers.add(updatedUser);
            }
            course.setUsers(updatedUsers);

            userRepository.saveAll(updatedUsers);
            courseRepository.save(course);
        } else {
            throw new InvalidEntityException("INVALID_COURSE");
        }
    }

}
