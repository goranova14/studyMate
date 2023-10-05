package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.UpdateAnnouncementRequest;
import nl.fontys.s3.studysmate.studymate.domain.UpdateAssignmentRequest;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UpdateAnnouncementUseCaseImplTest {

        @Mock
        private AnnouncementRepository announcementRepository;
    @Mock
    private AccessToken accessToken;
        @InjectMocks
        private UpdateAnnouncementUseCaseImpl updateAnnouncementUseCase;
    @Test
    void updateAnnouncement() {
        UserEntity user= UserEntity.builder().pcn(1l).build();
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder()
                .id(1L)
                .userEntity(user)
                .build();
        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                .id(1L)
                .build();
        when(announcementRepository.findById(request.getId())).thenReturn(Optional.of(announcementEntity));
        when(accessToken.getUserId()).thenReturn(1l);

        when(announcementRepository.save(announcementEntity)).thenReturn(announcementEntity);

        updateAnnouncementUseCase.updateAnnouncement(request);
        verify(announcementRepository).findById(request.getId());
        verify(accessToken).getUserId();
        verify(announcementRepository).save(announcementEntity);
        assertEquals(request.getId(), announcementEntity.getId());
    }    @Test
    void updateAnnouncementNegativeBecauseOfAccessToken() {
        UserEntity user= UserEntity.builder().pcn(1l).build();
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder()
                .id(1L)
                .userEntity(user)
                .build();
        UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                .id(1L)
                .build();
//        when(announcementRepository.findById(request.getId())).thenReturn(Optional.of(announcementEntity));
//        when(accessToken.getUserId()).thenReturn(2l);

//        when(announcementRepository.save(announcementEntity)).thenReturn(announcementEntity);

//        updateAnnouncementUseCase.updateAnnouncement(request);
//        verify(announcementRepository).findById(request.getId());
//        verify(accessToken).getUserId();
//        verify(announcementRepository).save(announcementEntity);
        assertThrows(InvalidEntityException.class, () -> updateAnnouncementUseCase.updateAnnouncement(request));
    }

        @Test
        void updateCourseNegative() {
            UpdateAnnouncementRequest request = UpdateAnnouncementRequest.builder()
                    .id(1L)
                    .build();
            when(announcementRepository.findById(request.getId())).thenReturn(Optional.empty());
            assertThrows(InvalidEntityException.class, () -> updateAnnouncementUseCase.updateAnnouncement(request));
            verify(announcementRepository).findById(request.getId());
        }
}