package nl.fontys.s3.studysmate.studymate.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStudentResponse {
    private long pcn;
}
