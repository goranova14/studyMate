package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.domain.User;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserConverterTest {

    @Test
    void convertPositive() {
        UserEntity userEntity=UserEntity.builder()
                .firstName("Denitsa")
                .address("Niue")
                .pcn(2L)
                .password("123")
                .email("d@abv.bg")
                .lastName("Got")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))

                .build();

        User user=UserConverter.convert(userEntity);
        assertEquals(userEntity.getPcn(),user.getPcn());
    }
}