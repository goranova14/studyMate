package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.domain.User;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class UserConverter {

    private UserConverter() {
    }

    public static User convert(UserEntity student) {

        return User.builder()
                .pcn(student.getPcn())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .course(student.getCourseEntity())
                .address(student.getAddress())
                .password(student.getPassword())
                .email(student.getEmail())
                .role(convertRoles(student.getUserRoles()))
                .build();

    }

    private static Roles convertRoles(Set<UserRoleEntity> userRoleEntities) {
        return userRoleEntities.stream()
                .findFirst()
                .map(UserRoleEntity::getRole)
                .orElse(null);}
    }




