package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity,Long> {
    List<AssignmentEntity> findAllByCourseEntity_Id(Long id);

    @Query("select a from AssignmentEntity a where a.courseEntity.id=:id order by a.deadline ASC ")
    List<AssignmentEntity> findAllByCourseEntity_IdDeadlineASC(@RequestParam Long id);
    @Query("select a from AssignmentEntity  a where a.courseEntity.id=:id order by a.deadline DESC")
    List<AssignmentEntity> findAllByCourseEntity_IdDeadlineDESC(@RequestParam Long id);

}
