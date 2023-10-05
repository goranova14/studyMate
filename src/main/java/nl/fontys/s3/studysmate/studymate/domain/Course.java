package nl.fontys.s3.studysmate.studymate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String name;
    private  String description;
    private Long id;
    private List<User> users;
    private List<AnnouncementEntity> announcements;

}
