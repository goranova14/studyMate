package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateNotificationUseCase;
import nl.fontys.s3.studysmate.studymate.business.DeleteNotificationUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetNotificationsUseCase;
import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private final CreateNotificationUseCase createNotificationUseCase;
    private final GetNotificationsUseCase getNotificationsUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;

    @IsAuthenticated
    @RolesAllowed(("TEACHER"))
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping()
    public ResponseEntity<Response> createNotification(@RequestBody @Valid CreateNotificationRequest request) {
        Response response = createNotificationUseCase.createNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @IsAuthenticated
    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteNotification(@PathVariable long id) {
       Response response= deleteNotificationUseCase.deleteNotification(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @IsAuthenticated
    @GetMapping("/student/{studentPcn}")
    public ResponseEntity<GetAllNotificationsResponse> getAllNotificationsForUser(@PathVariable(value="studentPcn") Long studentPcn) {

        GetAllNotificationsResponse response = getNotificationsUseCase.getNotifications(studentPcn);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
