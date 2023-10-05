package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/courses/{courseId}/announcements")
@AllArgsConstructor
public class AnnouncementController {
    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final GetAnnouncementsUseCase getAnnouncementsUseCase;
    private final GetAnnouncementByTitleUseCase getAnnouncementByTitleUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;
    private final DeleteAnnouncementUseCase deleteAnnouncementUseCase;
    private final CourseRepository courseRepository;

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @PostMapping()
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@PathVariable Long courseId,
                                                                         @RequestBody CreateAnnouncementRequest request) {

        Optional<CourseEntity> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            request.setCourse(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAnnouncement(@PathVariable("id") long id,
                                                       @RequestBody @Valid UpdateAnnouncementRequest request) {
        request.setId(id);
        updateAnnouncementUseCase.updateAnnouncement(request);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @GetMapping
    public ResponseEntity<GetAllAnnouncementsResponse> getAllAnnouncementsForCourse(@PathVariable("courseId") Long courseId) {
        GetAllAnnouncementsRequest request = GetAllAnnouncementsRequest.builder()
                .courseId(courseId)
                .build();
        GetAllAnnouncementsResponse response = getAnnouncementsUseCase.getAnnouncements(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @IsAuthenticated
    @GetMapping("/title")
    public ResponseEntity<GetAllAnnouncementsResponse> getAllAnnouncementsForCourseByTitle(@RequestParam("courseId") Long courseId,@RequestParam String title){

        GetAllAnnouncementsResponse response = getAnnouncementByTitleUseCase.getAnnouncements(title,courseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAnnouncement(@PathVariable long id) {

            deleteAnnouncementUseCase.deleteAnnouncement(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
