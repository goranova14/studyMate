package nl.fontys.s3.studysmate.studymate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {

    private String subject;
    private List<String> roles;
    private Long userId;

    @JsonIgnore
    public boolean hasRole(String roleName){
     if (roles==null){
         return  false;
     }
     return roles.contains(roleName);
    }
}
