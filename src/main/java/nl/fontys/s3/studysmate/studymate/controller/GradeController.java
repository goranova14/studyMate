package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/courses/{courseId}/assignments/{assignmentId}/grades")
@AllArgsConstructor
public class GradeController {
    private final CreateGradeUseCase createGradeUseCase;
    private final UpdateGradeUseCase updateGradeUseCase;
    private final DeleteGradeUseCase deleteGradeUseCase;
    private final GetGradeUseCase getGradeUseCase;
    private final GetStudentsGradesUseCase getStudentsGradesUseCase;
    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @GetMapping("/{requestNum}")
    @Transactional
    public ResponseEntity<GetAllGradesForUserResponse> getGradesForUser(@PathVariable int requestNum) {
        GetAllGradesForUserResponse response = getStudentsGradesUseCase.getStudents(requestNum);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @PostMapping()
    @Transactional
    public ResponseEntity<CreateGradeResponse> createGrade(@RequestBody CreateGradeRequest request) {
        CreateGradeResponse response = createGradeUseCase.createGrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping()
    public ResponseEntity<Response> updateGrade(@RequestBody @Valid UpdateGradeRequest request) {
        updateGradeUseCase.updateGrade(request);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @IsAuthenticated
    @GetMapping("/{assignmentID}/{studentPcn}")
    public ResponseEntity<Object> getGradeFromStudentPcnandAssginmentId(@PathVariable("assignmentID") long assignmentId,
                                                                        @PathVariable("studentPcn") long studentPcn
    ) {
        Grade grade = getGradeUseCase.getGrade(assignmentId, studentPcn);
        return ResponseEntity.status(HttpStatus.OK).body(grade);

    }

    @Transactional
    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/{assignmentID}/{studentPcn}")
    public ResponseEntity<Response> deleteGrade(@PathVariable("assignmentID") long assignmentId,
                                                @PathVariable("studentPcn") long studentPcn) {

        deleteGradeUseCase.deleteGrade(assignmentId, studentPcn);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
