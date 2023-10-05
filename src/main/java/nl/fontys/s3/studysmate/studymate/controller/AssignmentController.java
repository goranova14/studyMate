package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/courses/{courseId}/assignments")
@AllArgsConstructor
public class AssignmentController {
    private final CreateAssignmentUseCase createAssignmentUseCase;
    private final UpdateAssignmentUseCase updateAssignmentUseCase;
    private final DeleteAssignmentUseCase deleteAssignmentUseCase;
    private final GetAssignmentsUseCase getAssignmentsUseCase;
    private final CourseRepository courseRepository;
    private final UploadAssignmentUseCase uploadAssignmentUseCase;
    private final GetUploadedAssignmentUseCase getUploadedAssignmentUseCase;
    private final GetAssignmentsDeadlineASCUseCase getAssignmentsDeadlineASCUseCase;
    private final GetAssignmentsDeadlineDESCUseCase getAssignmentsDeadlineDESCUseCase;

    @IsAuthenticated
    @PostMapping("/uploadAssignment/{studentPCN}/{assignmentId}")
    public ResponseEntity<Object> uploadAssignment(@ModelAttribute("assignment") MultipartFile assignment,
                                                   @PathVariable(value = "studentPCN") Long studentPcn,
                                                   @PathVariable(value = "assignmentId") Long assignmentId) {
        UploadAssignmentResponse response = UploadAssignmentResponse.builder().message("Successfully uploaded").build();
        uploadAssignmentUseCase.uploadAssignment(assignment, studentPcn, assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @IsAuthenticated
    @GetMapping("/{assignmentId}/{studentPcn}")
    public ResponseEntity<Object> getUploadedAssignment(@PathVariable(value = "assignmentId") Long assignmentId, @PathVariable(value = "studentPcn") Long studentPcn) {
        GetUploadedAssignmentResponse response = getUploadedAssignmentUseCase.getUrl(assignmentId, studentPcn);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @PostMapping()
    @Transactional
    public ResponseEntity<CreateAssignmentResponse> createAssignment(@PathVariable Long courseId, @RequestBody CreateAssignmentRequest request) {
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new InvalidEntityException("Course not found"));
        request.setCourseEntity(courseEntity);
        CreateAssignmentResponse response = createAssignmentUseCase.createAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAssignment(@PathVariable("id") long id,
                                                     @RequestBody @Valid UpdateAssignmentRequest request) {
        request.setId(id);
        updateAssignmentUseCase.updateAssignment(request);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("{id}")
//    @SentrySpan
    public ResponseEntity<Void> deleteAssignment(@PathVariable long id) {
        deleteAssignmentUseCase.deleteAssignment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @IsAuthenticated
    @GetMapping
    public ResponseEntity<GetAllAssignmentsResponse> getAllAssignmentsForCourse(@PathVariable("courseId") Long courseId) {
        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder()
                .courseId(courseId)
                .build();
        GetAllAssignmentsResponse response = getAssignmentsUseCase.getAssignments(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @GetMapping("/deadlineASC")
    public ResponseEntity<GetAllAssignmentsResponse> getAllAssignmentsForCourseDeadlineASC(@PathVariable("courseId") Long courseId) {
        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder()
                .courseId(courseId)
                .build();
        GetAllAssignmentsResponse response = getAssignmentsDeadlineASCUseCase.getAssignments(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @GetMapping("/deadlineDESC")
    public ResponseEntity<GetAllAssignmentsResponse> getAllAssignmentsForCourseDeadlineDESC(@PathVariable("courseId") Long courseId) {
        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder()
                .courseId(courseId)
                .build();
        GetAllAssignmentsResponse response = getAssignmentsDeadlineDESCUseCase.getAssignments(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
