package nl.fontys.s3.studysmate.studymate.controller;

import jakarta.validation.ConstraintViolationException;
import jdk.javadoc.doclet.Reporter;
import nl.fontys.s3.studysmate.studymate.business.*;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.impl.CreateAnnouncementUseCaseImpl;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementControllerTest {

    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private AnnouncementController announcementController;
    @Mock//Mockito-basically create a mock object of the class
    private CreateAnnouncementUseCase createAnnouncementUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private UpdateAnnouncementUseCase updateAnnouncementUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private GetAnnouncementsUseCase getAnnouncementsUseCase;
    @Mock//Mockito-basically create a mock object of the class
    private DeleteAnnouncementUseCase deleteAnnouncementUseCase;
    @Mock
    private GetAnnouncementByTitleUseCase getAnnouncementByTitleUseCase;

    @Test
    void CreateAnnouncementUseCaseInvalidCourse() {
        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .title("Announcement").description("description")
                .build();

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<CreateAnnouncementResponse> response = announcementController.createAnnouncement(1L, request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createAnnouncement() {
        CreateAnnouncementRequest request = CreateAnnouncementRequest.builder()
                .title("Announcement").description("description")
                .build();

        CourseEntity course = CourseEntity.builder()
                .id(1L).name("course")
                .build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        CreateAnnouncementResponse expectedResponse = CreateAnnouncementResponse.builder()
                .title("Announcement")
                .build();

        when(createAnnouncementUseCase.createAnnouncement(request)).thenReturn(expectedResponse);


        ResponseEntity<CreateAnnouncementResponse> responseEntity = announcementController.createAnnouncement(1L, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(createAnnouncementUseCase).createAnnouncement(request);
        verify(courseRepository).findById(1L);

    }

    @Test
    void UpdateAnnouncementShouldReturnOK() {
        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                .title("New announcement")
                .description("No classes")
                .id(1L)
                .build();
        doNothing().when(updateAnnouncementUseCase).updateAnnouncement(request);

        ResponseEntity<Response> response = announcementController.updateAnnouncement(1L, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


//    @Test
//    void updateAnnouncementShouldThrowException() {
//        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
//                .description("No classes")
//                .id(1L)
//                .build();
//
//        doThrow(new InvalidEntityException("INVALID_ID")).when(updateAnnouncementUseCase).updateAnnouncement(request);
//        ResponseEntity<Response> response = announcementController.updateAnnouncement(1L, request);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

    @Test
    void getAnnouncementsPositive() {
        Announcement announcement = Announcement.builder()
                .title("bla")
                .build();
        Announcement announcement2 = Announcement.builder()
                .title("bla")
                .build();
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        announcements.add(announcement2);

        when(getAnnouncementsUseCase.getAnnouncements(any(GetAllAnnouncementsRequest.class))).thenReturn(GetAllAnnouncementsResponse.builder().announcementEntityList(announcements).build());
        ResponseEntity<GetAllAnnouncementsResponse> response = announcementController.getAllAnnouncementsForCourse(1L);
        assertEquals(announcements, response.getBody().getAnnouncementEntityList());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(getAnnouncementsUseCase).getAnnouncements(any(GetAllAnnouncementsRequest.class));

    }  @Test
    void getAnnouncementsByTitle() {
        Announcement announcement = Announcement.builder()
                .courseEntityId(2l)
                .title("bla")
                .build();
        Announcement announcement2 = Announcement.builder()
                .title("bla")
                .courseEntityId(2l)
                .build();
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        announcements.add(announcement2);
        when(getAnnouncementByTitleUseCase.getAnnouncements("a",2l)).thenReturn(GetAllAnnouncementsResponse.builder().announcementEntityList(announcements).build());
        ResponseEntity<GetAllAnnouncementsResponse> response = announcementController.getAllAnnouncementsForCourseByTitle(2L,"a");
        assertEquals(announcements, response.getBody().getAnnouncementEntityList());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(getAnnouncementByTitleUseCase).getAnnouncements("a",2l);

    }


    @Test
    void deleteAnnouncementPositive() {
        ResponseEntity<Object> response = announcementController.deleteAnnouncement(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
//    @Test
//    void deleteAnnouncementNegative() {
//        InvalidEntityException exception = new InvalidEntityException("sa");
//
//        doThrow(exception).when(deleteAnnouncementUseCase).deleteAnnouncement(1l);
//        ResponseEntity<Object> response = announcementController.deleteAnnouncement(1L);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
}
