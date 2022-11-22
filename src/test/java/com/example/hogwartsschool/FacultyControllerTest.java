package com.example.hogwartsschool;

import com.example.hogwartsschool.component.RecordMapper;
import com.example.hogwartsschool.controller.FacultyController;
import com.example.hogwartsschool.entity.Faculty;
import com.example.hogwartsschool.record.FacultyRecord;
import com.example.hogwartsschool.repositories.FacultyRepository;
import com.example.hogwartsschool.service.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private RecordMapper recordMapper;

    @Test
    public void createTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("Красный");
        faculty.setName("Гриффиндор");

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);


        FacultyRecord facultyRecord = new FacultyRecord();
        faculty.setId(1L);
        facultyRecord.setColor("Красный");
        facultyRecord.setName("Гриффиндор");

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facultyRecord)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    FacultyRecord facultyRecordResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), FacultyRecord.class);
                    Assertions.assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    Assertions.assertThat(facultyRecordResult).isNotNull();
                    Assertions.assertThat(facultyRecordResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(facultyRecord);
                    Assertions.assertThat(facultyRecordResult.getId()).isEqualTo(faculty.getId());
                });
    }
}
