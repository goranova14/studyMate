package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateCourseUseCase;
import nl.fontys.s3.studysmate.studymate.domain.CreateCourseRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateCourseResponse;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCourseUseCaseImpl  implements CreateCourseUseCase {
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        CourseEntity newCourse = createNewCourse(request);
        userRepository.saveAll(newCourse.getUsers());

        return CreateCourseResponse.builder()
                .id(newCourse.getId())
                .title(newCourse.getName())

                .build();
    }

    private CourseEntity createNewCourse(CreateCourseRequest request) {
        CourseEntity newCourse=CourseEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .users(request.getUsers())
                .build();
        for (UserEntity user: newCourse.getUsers()){
            user.setCourseEntity(newCourse);
        }
        return courseRepository.save(newCourse);
    }
}
