package nl.fontys.s3.studysmate.studymate.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserEntityTest {

    @Test
    void testToString() {
       UserEntity userEntity = UserEntity.builder()
                .pcn(1l)
                .firstName("s")
                .lastName("sa")
                .email("sa")
               .address("sa")
                .build();
        assertNotNull(userEntity);
        assertEquals("UserEntity{" +
                "pcn=" + userEntity.getPcn() +
                ", firstName='" + userEntity.getFirstName() + '\'' +
                ", lastName='" + userEntity.getLastName() + '\'' +
                ", address='" + userEntity.getAddress() + '\'' +
                ", email='" + userEntity.getEmail() + '\'' +
                '}', userEntity.toString());
    }
}