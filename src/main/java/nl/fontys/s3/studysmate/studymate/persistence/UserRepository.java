package nl.fontys.s3.studysmate.studymate.persistence;

import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends  JpaRepository<UserEntity,Long> {

    boolean existsByPcn(long pcn);
    Optional<UserEntity> findByPcn(long pcn);
    Optional<UserEntity> findByEmail(String email);
    void deleteByPcn(long pcn);



}
