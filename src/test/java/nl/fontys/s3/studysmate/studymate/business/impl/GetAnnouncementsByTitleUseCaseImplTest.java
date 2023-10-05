package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Announcement;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAnnouncementsResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class GetAnnouncementsByTitleUseCaseImplTest {
    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private GetAnnouncementsByTitleUseCaseImpl getAnnouncementsUseCase;

    @Test
    void getAnnouncements() {
        CourseEntity courseEntity = CourseEntity.builder().id(1L).build();
        UserEntity user=UserEntity.builder().email("deni@abv.bg").build();

        AnnouncementEntity announcementEntity1 = AnnouncementEntity.builder()
                .id(1l)
                .courseEntity(courseEntity)
                .userEntity(user)
                .title("app")
                .build();
        announcementRepository.save(announcementEntity1);

        AnnouncementEntity announcementEntity2 = AnnouncementEntity.builder()
                .id(2l)
                .courseEntity(courseEntity)
                .userEntity(user)
                .title("ba")
                .build();

        when(announcementRepository.findByTitleContainingIgnoreCaseAndCourseEntity_Id("a",1L))
                .thenReturn(List.of(announcementEntity1, announcementEntity2));

        GetAllAnnouncementsResponse response = getAnnouncementsUseCase.getAnnouncements("a",1l);

        List<Announcement> announcements = response.getAnnouncementEntityList();

        assertEquals(2, announcements.size());
        verify(announcementRepository).findByTitleContainingIgnoreCaseAndCourseEntity_Id("a",1L);
    }

    @Test
    void getAnnouncementsShouldThrowAnException(){
        when(announcementRepository.findByTitleContainingIgnoreCaseAndCourseEntity_Id("a",1L)).thenReturn(Collections.emptyList());
        assertThrows(InvalidEntityException.class, ()
                -> getAnnouncementsUseCase.getAnnouncements("a",1l));
        verify(announcementRepository).findByTitleContainingIgnoreCaseAndCourseEntity_Id("a",1L);
    }
}