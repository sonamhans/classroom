package com.lloyds.classroom_service.controller;

import com.lloyds.classroom_service.dto.ClassroomFindByIdsRequestDto;
import com.lloyds.classroom_service.exception.ClassroomNotFoundException;
import com.lloyds.classroom_service.model.Classroom;
import com.lloyds.classroom_service.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping(value = "/classrooms")
    public List<Classroom> getAllClassroom() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping(value = "/classroom/{id}")
    public Classroom getClassroom(@PathVariable("id") int id) throws ClassroomNotFoundException {
        return classroomService.getClassroomById(id);
    }

    @DeleteMapping(value = "/classroom/{id}")
    public int deleteClassroom(@PathVariable("id") int id) {
        return classroomService.delete(id);
    }

    @PostMapping(value = "/classroom")
    public Classroom saveClassroom(@RequestBody Classroom classroom) {
        return classroomService.save(classroom);
    }

    @PostMapping(value = "/classroom/find-by-ids")
    public List<Classroom> getAllClassroomByIds(@RequestBody ClassroomFindByIdsRequestDto requestDto) {
        return classroomService.getClassroomByIds(requestDto);
    }

    @PutMapping(value = "/classroom/{id}")
    public Classroom updateClassroom(@RequestBody Classroom classroom, @PathVariable("id") Integer id) throws ClassroomNotFoundException {
        return classroomService.update(classroom, id);
    }
}
