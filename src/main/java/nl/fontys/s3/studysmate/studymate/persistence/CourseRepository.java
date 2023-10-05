package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {

}
