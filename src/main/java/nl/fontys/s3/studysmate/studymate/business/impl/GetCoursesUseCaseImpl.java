package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetCoursesUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetCoursesUseCaseImpl implements GetCoursesUseCase {

    private CourseRepository courseRepository;

    @Override
    public GetAllCoursesResponse getCourses(GetAllCoursesRequest request) {

        List<CourseEntity> courseEntities = courseRepository.findAll();
        final GetAllCoursesResponse response = new GetAllCoursesResponse();
        List<Course> results = courseEntities
                .stream()
                .map(CourseConverter::convert)
                .toList();
        response.setCourses(results);
        if (results.isEmpty()) {
            throw new InvalidEntityException("No courses found");
        } else {
            return response;
        }
    }
}
