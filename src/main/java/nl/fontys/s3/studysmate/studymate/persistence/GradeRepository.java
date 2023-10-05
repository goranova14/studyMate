package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

    Optional<GradeEntity> findByAssignmentEntityIdAndUserEntityPcn(Long assignmentId, Long userId);

    List<GradeEntity> findAllByUserEntityPcn(Long userId);

    @Query("select avg(g.grade) from GradeEntity g where g.userEntity.pcn = :studentPcn")
    Double getAverageGradeForUser(@RequestParam("studentPcn") Long studentPcn);

    @Query("select g from GradeEntity g where g.grade>=:minGrade")
    List<GradeEntity> getGradeByUserEntityPcn(@RequestParam("minGrade") int minGrade);
}
