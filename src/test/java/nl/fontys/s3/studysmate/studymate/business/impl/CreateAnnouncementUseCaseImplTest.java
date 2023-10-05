package nl.fontys.s3.studysmate.studymate.business.impl;

import com.sun.jdi.request.InvalidRequestStateException;
import nl.fontys.s3.studysmate.studymate.business.CreateAnnouncementUseCase;
import nl.fontys.s3.studysmate.studymate.domain.CreateAnnouncementRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateAnnouncementResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAnnouncementUseCaseImplTest {

    @Mock
    private AnnouncementRepository announcementRepository;
    @InjectMocks
    private CreateAnnouncementUseCaseImpl createAnnouncementUseCase;

    @Test
    void createAnnouncement() {
        CreateAnnouncementRequest request = new CreateAnnouncementRequest();
        request.setTitle("Title");
        request.setDescription("Description");

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(1L);

        request.setCourse(courseEntity);

        AnnouncementEntity announcementEntity = AnnouncementEntity.builder().id(1L)
                .title(request.getTitle())
                .description(request.getDescription())
                .courseEntity(request.getCourse())
                .build();


        when(announcementRepository.save(any(AnnouncementEntity.class))).thenReturn(announcementEntity);

        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);
        verify(announcementRepository).save(any(AnnouncementEntity.class));
        assertEquals(response.getTitle(), announcementEntity.getTitle());
    }

    @Test
    void createAnnouncement_InvalidRequest() {
        CreateAnnouncementRequest request= CreateAnnouncementRequest.builder().build();
        assertThrows(NullPointerException.class,()->createAnnouncementUseCase.createAnnouncement(request));

    }
}
