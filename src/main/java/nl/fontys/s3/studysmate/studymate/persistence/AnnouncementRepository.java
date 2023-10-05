package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity,Long> {
    List<AnnouncementEntity> findAllByCourseEntity_Id(Long id);
    List<AnnouncementEntity> findByTitleContainingIgnoreCaseAndCourseEntity_Id(String query, Long courseId);

}
