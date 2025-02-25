package com.lloyds.classroom_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloyds.classroom_service.dto.ClassroomFindByIdsRequestDto;
import com.lloyds.classroom_service.exception.ClassroomNotFoundException;
import com.lloyds.classroom_service.model.Classroom;
import com.lloyds.classroom_service.service.ClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ClassroomController.class)
public class ClassroomControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClassroomService classroomService;

    @Autowired
    private ObjectMapper objectMapper;

    private Classroom classroom1;
    private Classroom classroom2;

    @BeforeEach
    public void setUp() {
        classroom1 = new Classroom(1, "Classroom 1", 123, 1);
        classroom2 = new Classroom(2, "Classroom 2", 456, 1);
    }

    @Test
    public void testGetAllClassroom() throws Exception {
        // Given
        when(classroomService.getAllClassrooms()).thenReturn(Arrays.asList(classroom1, classroom2));

        // When & Then
        mockMvc.perform(get("/classrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Classroom 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Classroom 2")));
    }

    @Test
    public void testGetClassroom() throws Exception {
        // Given
        when(classroomService.getClassroomById(anyInt())).thenReturn(classroom1);

        // When & Then
        mockMvc.perform(get("/classroom/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Classroom 1")));
    }

    @Test
    public void testSaveClassroom() throws Exception {
        // Given
        when(classroomService.save(any(Classroom.class))).thenReturn(classroom1);

        // When & Then
        mockMvc.perform(post("/classroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classroom1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Classroom 1")));
    }

    @Test
    public void testDeleteClassroom() throws Exception {
        // Given
        // No need to mock delete method as it returns void

        // When & Then
        mockMvc.perform(delete("/classroom/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllClassroomByIds() throws Exception {
        // Given
        ClassroomFindByIdsRequestDto dto = new ClassroomFindByIdsRequestDto();
        dto.setIds(Arrays.asList(1, 2));
        when(classroomService.getClassroomByIds(any(ClassroomFindByIdsRequestDto.class))).thenReturn(Arrays.asList(classroom1, classroom2));

        // When & Then
        mockMvc.perform(post("/classroom/find-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Classroom 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Classroom 2")));
    }

    @Test
    public void testUpdateClassroom() throws Exception {
        // Given
        Classroom updatedClassroom = new Classroom(1, "Updated Classroom", 123, 1);
        when(classroomService.update(any(Classroom.class), anyInt())).thenReturn(updatedClassroom);

        // When & Then
        mockMvc.perform(put("/classroom/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClassroom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Classroom")));
    }
}