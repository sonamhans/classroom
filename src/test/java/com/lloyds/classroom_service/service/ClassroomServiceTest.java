package com.lloyds.classroom_service.service;

import com.lloyds.classroom_service.converter.ClassroomConverter;
import com.lloyds.classroom_service.dto.ClassroomFindByIdsRequestDto;
import com.lloyds.classroom_service.exception.ClassroomNotFoundException;
import com.lloyds.classroom_service.model.Classroom;
import com.lloyds.classroom_service.repository.ClassroomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private ClassroomConverter classroomConverter;

    @InjectMocks
    private ClassroomService classroomService;

    private Classroom classroom1;
    private Classroom classroom2;

    @BeforeEach
    public void setUp() {
        classroom1 = new Classroom(1, "Classroom 1", 123, 1);
        classroom2 = new Classroom(2, "Classroom 1", 456, 1);
    }

    @Test
    public void testGetAllClassrooms() {
        // Given
        when(classroomRepository.findAll()).thenReturn(Arrays.asList(classroom1, classroom2));

        // When
        List<Classroom> classrooms = classroomService.getAllClassrooms();

        // Then
        assertThat(classrooms).hasSize(2).containsExactlyInAnyOrder(classroom1, classroom2);
    }

    @Test
    public void testGetClassroomById() throws ClassroomNotFoundException {
        // Given
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom1));

        // When
        Classroom classroom = classroomService.getClassroomById(1);

        // Then
        assertThat(classroom).isEqualTo(classroom1);
    }

    @Test
    public void testGetClassroomByIdThrowsException() {
        // Given
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> classroomService.getClassroomById(1))
                .isInstanceOf(ClassroomNotFoundException.class)
                .hasMessage("Classroom not found");
    }

    @Test
    public void testSaveClassroom() {
        // Given
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom1);

        // When
        Classroom savedClassroom = classroomService.save(classroom1);

        // Then
        assertThat(savedClassroom).isEqualTo(classroom1);
    }

    @Test
    public void testGetClassroomsByIds() {
        // Given
        ClassroomFindByIdsRequestDto dto = new ClassroomFindByIdsRequestDto();
        dto.setIds(Arrays.asList(1, 2));
        when(classroomRepository.findAllById(dto.getIds())).thenReturn(Arrays.asList(classroom1, classroom2));

        // When
        List<Classroom> classrooms = classroomService.getClassroomByIds(dto);

        // Then
        assertThat(classrooms).hasSize(2).containsExactlyInAnyOrder(classroom1, classroom2);
    }

    @Test
    public void testDeleteClassroom() {
        // Given
        doNothing().when(classroomRepository).deleteById(1);

        // When
        int deletedId = classroomService.delete(1);

        // Then
        assertThat(deletedId).isEqualTo(1);
        verify(classroomRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateClassroom() throws ClassroomNotFoundException {
        // Given
        Classroom updatedClassroom = new Classroom(1, "Classroom 1", 123, 1);

        when(classroomRepository.findById(anyInt())).thenReturn(Optional.of(classroom1));
        when(classroomConverter.convert(any(Classroom.class), any(Classroom.class))).thenReturn(updatedClassroom);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(updatedClassroom);

        // When
        Classroom result = classroomService.update(updatedClassroom, 1);

        // Then
        assertThat(result).isEqualTo(updatedClassroom);
    }

    @Test
    public void testUpdateClassroomThrowsException() {
        // Given
        Classroom updatedClassroom = new Classroom(1, "Classroom 1", 123, 1);
        when(classroomRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> classroomService.update(updatedClassroom, 1))
                .isInstanceOf(ClassroomNotFoundException.class)
                .hasMessage("");
    }
}