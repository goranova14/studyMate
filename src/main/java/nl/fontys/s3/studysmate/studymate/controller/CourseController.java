package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateCourseUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetCourseUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetCoursesUseCase;
import nl.fontys.s3.studysmate.studymate.business.UpdateCourseUseCase;
import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
    private final CreateCourseUseCase createCourseUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final GetCoursesUseCase getCoursesUseCase;
    private final GetCourseUseCase getCourseUseCase;

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping()
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody @Valid CreateCourseRequest request) {
        CreateCourseResponse response = createCourseUseCase.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCourse(@PathVariable("id") long id,
                                                 @RequestBody @Valid UpdateCourseRequest request) {
        request.setId(id);
        updateCourseUseCase.updateCourse(request);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @GetMapping
    public ResponseEntity<GetAllCoursesResponse> getAllCourses() {
        GetAllCoursesRequest request = GetAllCoursesRequest.builder().build();
        GetAllCoursesResponse response = getCoursesUseCase.getCourses(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @GetMapping("{id}")
    public ResponseEntity<Course> getCourseId(@PathVariable(value = "id") final long id) {
        final Optional<Course> courseOptional = getCourseUseCase.getCourse(id);
        return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
    }


    @IsAuthenticated
    @GetMapping("students/{id}")
    public ResponseEntity<GetAllStudentsResponse> getUsersForCourse(@PathVariable(value = "id") final long id) {
        final Optional<Course> courseOptional = getCourseUseCase.getCourse(id);
        GetAllStudentsResponse response = GetAllStudentsResponse.builder().users(courseOptional.get().getUsers()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
