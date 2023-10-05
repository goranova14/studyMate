package nl.fontys.s3.studysmate.studymate.persistence;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryRepo {
    String uploadAssignment(MultipartFile assignment);
}
