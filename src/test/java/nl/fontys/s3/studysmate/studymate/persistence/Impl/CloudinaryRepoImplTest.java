package nl.fontys.s3.studysmate.studymate.persistence.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryRepoImplTest {

    @InjectMocks
    private CloudinaryRepoImpl cloudinaryRepo;

    @Test
    void uploadAssignmentPositive() {
        MockMultipartFile assignment = new MockMultipartFile("assignment", "assignment.txt", "text/plain", "a".getBytes());
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "https://cloudinary.com/example/assignment.txt");

        String assignmentUrl = cloudinaryRepo.uploadAssignment(assignment);

        assertNotNull(assignmentUrl);
    }



}