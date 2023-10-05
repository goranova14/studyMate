package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.persistence.AnnouncementRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAnnouncementUseCaseImplTest {

    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private DeleteAnnouncementCaseImpl deleteAnnouncementUseCase;

    @Test
    void deleteAnnouncementShouldReturnVoid() {
        UserEntity user = UserEntity.builder()
                .address("Address").pcn(2L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder().id(1L).userEntity(user).title("course").build();
        when(announcementRepository.findById(announcementEntity.getId())).thenReturn(Optional.of(announcementEntity));
        when(accessToken.getUserId()).thenReturn(2l);

        deleteAnnouncementUseCase.deleteAnnouncement(announcementEntity.getId());
        verify(accessToken).getUserId();
        assertFalse(announcementRepository.existsById(announcementEntity.getId()));

    }
    @Test
    void deleteAnnouncementShouldThrowExceptionIfAccessTokenIdIsNotTheSameAsInAnnouncement() {
        UserEntity user = UserEntity.builder()
                .address("Address").pcn(2L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder().id(1L).userEntity(user).title("course").build();
        when(announcementRepository.findById(announcementEntity.getId())).thenReturn(Optional.of(announcementEntity));

        assertThrows(InvalidEntityException.class, () -> deleteAnnouncementUseCase.deleteAnnouncement(announcementEntity.getId()));

    }
    @Test
    void deleteAnnouncementShouldThrowAnInvalidStudentException() {
        AnnouncementEntity announcementEntity = AnnouncementEntity.builder().id(1L).title("title").build();
        when(announcementRepository.findById(announcementEntity.getId())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> deleteAnnouncementUseCase.deleteAnnouncement(announcementEntity.getId()));
        verify(announcementRepository).findById(announcementEntity.getId());
    }
}