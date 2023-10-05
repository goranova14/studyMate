package nl.fontys.s3.studysmate.studymate.controllerintegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.fontys.s3.studysmate.studymate.business.CreateTeacherUseCase;
import nl.fontys.s3.studysmate.studymate.business.CreateUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetStudentsUseCase;
import nl.fontys.s3.studysmate.studymate.business.GetUserEmailUseCase;
import nl.fontys.s3.studysmate.studymate.business.impl.GetUserPCNUseCaseImpl;
import nl.fontys.s3.studysmate.studymate.business.impl.LoginUseCaseImpl;
import nl.fontys.s3.studysmate.studymate.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private CreateTeacherUseCase createTeacherUseCase;
    @MockBean
    private GetStudentsUseCase getStudentsUseCase;
    @MockBean
    private GetUserPCNUseCaseImpl getUserPCNUseCase;

    @MockBean
    private LoginUseCaseImpl loginUseCase;
    @MockBean
    private GetUserEmailUseCase getUserEmailUseCase;

    @Test
    void loginSuccessful() throws Exception {
    LoginRequest request=LoginRequest.builder().email("denica@abv.bg").password("123").build();
    LoginResponse expectedResponse=LoginResponse.builder().accessToken("accessToken").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(request);

        when(getUserEmailUseCase.getStudent("denica@abv.bg")).thenReturn(Optional.of(new User()));
        when(loginUseCase.login(request)).thenReturn(expectedResponse);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));
        verify(loginUseCase, times(1)).login(request);

    }

    @Test
    void createStudentShouldBeSuccessful() throws Exception {
        CreateStudentRequest request = CreateStudentRequest.builder().firstName("Denitsa").lastName("Goranova").address("Niuewe").password("Bulgaria").email("de").pcn(1L).build();
        CreateStudentResponse expectedResponse = CreateStudentResponse.builder().pcn(1L).build();

        when(createUserUseCase.createStudent(request)).thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(request);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));
        verify(createUserUseCase, times(1)).createStudent(request);
    }  @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"})
    void createTeacherShouldBeSuccessful() throws Exception {
        CreateTeacherRequest request = CreateTeacherRequest.builder().firstName("Denitsa").lastName("Goranova").address("Niuewe").password("Bulgaria").email("de").pcn(1L).build();
        CreateTeacherResponse expectedResponse = CreateTeacherResponse.builder().pcn(1L).build();

        when(createTeacherUseCase.createTeacher(request)).thenReturn(expectedResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(request);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students/registerTeacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));
        verify(createTeacherUseCase, times(1)).createTeacher(request);
    }

    @Test
    void createStudentShouldBeNegative() throws Exception {
        CreateStudentRequest request = CreateStudentRequest.builder().password("12").email("ass").build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(request);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createTeacherShouldBeNegative() throws Exception {
        CreateTeacherRequest request = CreateTeacherRequest.builder().password("12").email("ass").build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJSON = ow.writeValueAsString(request);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void createTeacherShouldReturn403() throws Exception {
        mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void getAllStudentsShouldReturn403() throws Exception {
        mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllStudentsShouldReturn401() throws Exception {
        mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserPcnShouldReturn401() throws Exception {
        mockMvc.perform(get("/students/{id}", 1l).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"})
    void getAllStudents() throws Exception {
        GetAllStudentsRequest request = GetAllStudentsRequest.builder().build();

        User user = User.builder()
                .firstName("Mirela")
                .lastName("Goranova")
                .address("Street1")
                .password("Bulgaria")
                .pcn(2L).build();
        GetAllStudentsResponse response = GetAllStudentsResponse.builder().users(List.of(user)).build();
        when(getStudentsUseCase.getStudents(request)).thenReturn(response);

        mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json(
                        """ 
                                    {"users": [
                                        {
                                            "firstName": "Mirela",
                                            "lastName": "Goranova",
                                            "address": "Street1",
                                            "password": "Bulgaria",
                                            "pcn": 2
                                           
                                        }
                                    ]
                                }"""
                ));
        verify(getStudentsUseCase, times(1)).getStudents(request);


    }

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"})
    void getUserPCn() throws Exception {

        User user = User.builder()
                .firstName("Mirela")
                .lastName("Goranova")
                .address("Street1")
                .password("Bulgaria")
                .pcn(2L).build();
        when(getUserPCNUseCase.getStudent(2l)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/students/{id}", 2l).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json(
                        """ 
                                    {
                                        
                                            "firstName": "Mirela",
                                            "lastName": "Goranova",
                                            "address": "Street1",
                                            "password": "Bulgaria",
                                            "pcn": 2
                                           
                                        
                                    
                                }"""
                ));
        verify(getUserPCNUseCase, times(1)).getStudent(2l);


    }
//
//    @Test
//    void getStudentPCN() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void deleteStudent() {
//    }
//
//    @Test
//    void updateStudent() {
//    }
}