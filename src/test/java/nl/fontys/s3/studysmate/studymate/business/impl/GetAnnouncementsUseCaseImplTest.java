package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAnnouncementsUseCaseImplTest {

    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GetAnnouncementsUseCaseImpl getAnnouncementsUseCase;

    @Test
    void getAnnouncements() {
        UserEntity user=UserEntity.builder().email("deni@abv.bg").build();
        CourseEntity courseEntity = CourseEntity.builder().id(1L).build();
        courseRepository.save(courseEntity);

        AnnouncementEntity announcementEntity1 = AnnouncementEntity.builder()
                .courseEntity(courseEntity)
                .userEntity(user)
                .build();
        announcementRepository.save(announcementEntity1);

        AnnouncementEntity announcementEntity2 = AnnouncementEntity.builder()
                .courseEntity(courseEntity)
                .userEntity(user)

                .build();
        announcementRepository.save(announcementEntity2);

        when(announcementRepository.findAllByCourseEntity_Id(1L))
                .thenReturn(List.of(announcementEntity1, announcementEntity2));

        GetAllAnnouncementsRequest request = GetAllAnnouncementsRequest.builder()
                .courseId(1L)
                .build();
        GetAllAnnouncementsResponse response = getAnnouncementsUseCase.getAnnouncements(request);
        List<Announcement> announcements = response.getAnnouncementEntityList();

        assertEquals(2, announcements.size());
        verify(announcementRepository).findAllByCourseEntity_Id(1L);
    }

    @Test
    void getAnnouncementsShouldThrowAnException(){
        when(announcementRepository.findAllByCourseEntity_Id(1L)).thenReturn(Collections.emptyList());
        GetAllAnnouncementsRequest request = GetAllAnnouncementsRequest.builder().courseId(1L).build();
        assertThrows(InvalidEntityException.class, ()
                -> getAnnouncementsUseCase.getAnnouncements(request));
        verify(announcementRepository).findAllByCourseEntity_Id(1L);
    }
}