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

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/students")
@AllArgsConstructor
public class UserController {
    private final GetStudentPCNUseCase getStudentPCNUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final CreateTeacherUseCase createTeacherUseCase;
    private final GetStudentsUseCase getStudentsUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetAvgGradesForStudentUseCase getAvgGradesForStudentUseCase;

    private final UpdateUserUseCase updateUserUseCase;
    private final GetGradesForUserUseCase getGradesForUserUseCase;
    private final LoginUseCase loginUsecase;

    @CrossOrigin(origins = "http://localhost:3000/register")
    @PostMapping()
    public ResponseEntity<Object> createStudent(@RequestBody @Valid CreateStudentRequest request) {
            CreateStudentResponse response = createUserUseCase.createStudent(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/registerTeacher")
    public ResponseEntity<CreateTeacherResponse> createTeacher(@RequestBody @Valid CreateTeacherRequest request) {
        CreateTeacherResponse response = createTeacherUseCase.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @IsAuthenticated
    @GetMapping("/statistics/{id}")
    public ResponseEntity<Object> getAvgGradeForUser(@PathVariable(value = "id") final long id) {
        GetAvgGradesResponse response = getAvgGradesForStudentUseCase.getAvgGrade(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("grades/statistics/{id}")
    public ResponseEntity<GetAllGradesForUserResponse> getAllGrades(@PathVariable(value = "id") final long id) {
        GetAllGradesForUserResponse response = getGradesForUserUseCase.getGrades(id);
        return ResponseEntity.ok(response);

    }

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping
    public ResponseEntity<GetAllStudentsResponse> getAllStudents() {
        GetAllStudentsRequest request = GetAllStudentsRequest.builder().build();
        GetAllStudentsResponse response = getStudentsUseCase.getStudents(request);
        return ResponseEntity.ok(response);

    }

    @IsAuthenticated
    @RolesAllowed({"TEACHER", "STUDENT"})
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("{id}")
    public ResponseEntity<User> getStudentPCN(@PathVariable(value = "id") final long id) {
        final Optional<User> studentOptional = getStudentPCNUseCase.getStudent(id);
        return ResponseEntity.ok().body(studentOptional.get());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
        LoginResponse response = loginUsecase.login(loginRequest);
        LoginResponse newResponse = new LoginResponse(response.getAccessToken());
        return ResponseEntity.ok(newResponse);
    }

    @Transactional
    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("{studentPcn}")
    public ResponseEntity<Response> deleteStudent(@PathVariable long studentPcn) {
        deleteUserUseCase.deleteStudent(studentPcn);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @IsAuthenticated
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/settings/{pcn}")
    public ResponseEntity<Response> updateStudent(@PathVariable("pcn") long pcn,
                                                  @RequestBody @Valid UpdateStudentRequest request) {
        request.setPcn(pcn);
        updateUserUseCase.updateStudent(request);
        Response response = Response.builder().message("Successfully updated").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}

