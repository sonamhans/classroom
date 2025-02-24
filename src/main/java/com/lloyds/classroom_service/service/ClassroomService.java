package com.lloyds.classroom_service.service;

import com.lloyds.classroom_service.converter.ClassroomConverter;
import com.lloyds.classroom_service.dto.ClassroomFindByIdsRequestDto;
import com.lloyds.classroom_service.exception.ClassroomNotFoundException;
import com.lloyds.classroom_service.model.Classroom;
import com.lloyds.classroom_service.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;


    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }
    private ClassroomConverter classroomConverter;
    public List<Classroom> getClassroomByIds( ClassroomFindByIdsRequestDto requestDto) {
        return classroomRepository.findAllById(requestDto.getIds());
    }

    public Classroom getClassroomById(int id) throws ClassroomNotFoundException {
        return classroomRepository.findById(id).orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));
    }
    public Classroom save(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public int delete(int id) {
        classroomRepository.deleteById(id);
        return id;
    }

    public Classroom update(Classroom newClassroom, Integer id) throws ClassroomNotFoundException {
        Optional<Classroom> opt = classroomRepository.findById(id);
        Classroom classroom = classroomConverter.convert(newClassroom, opt.orElseThrow(() -> new ClassroomNotFoundException("")));
        return classroomRepository.save(classroom);
    }
}
