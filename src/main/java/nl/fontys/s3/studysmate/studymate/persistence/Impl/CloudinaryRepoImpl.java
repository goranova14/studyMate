package nl.fontys.s3.studysmate.studymate.persistence.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import nl.fontys.s3.studysmate.studymate.persistence.CloudinaryRepo;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CloudinaryRepoImpl implements CloudinaryRepo {

    @Override
    public String uploadAssignment(MultipartFile assignment) {
        Map config = new HashMap();
        config.put("cloud_name", "df2obsmjq");
        config.put("api_key", "247577937679859");
        config.put("api_secret", "6IcMVxTZoi1YlJe8rxNl1tMXR4E");
        Cloudinary cloudinary = new Cloudinary(config);
        try {
            String fileName = assignment.getOriginalFilename();

            Map<String, Object> uploadResult = cloudinary.uploader().upload(assignment.getBytes(), ObjectUtils.asMap("resource_type", "raw", "format", "docza", "public_id", fileName));
            return uploadResult.get("url").toString();

        } catch (IOException exception) {
        }
        return null;
    }
}
