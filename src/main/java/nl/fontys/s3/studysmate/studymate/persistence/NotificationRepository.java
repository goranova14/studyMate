package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    List<NotificationEntity> findAllByUserEntity_Pcn(Long id);

}
