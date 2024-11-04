package com.student.student.service;

import com.student.student.dto.StudentDto;
import com.student.student.model.Student;
import com.student.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::mapToDto).orElse(null);
    }

    public StudentDto createStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        return mapToDto(studentRepository.save(student));
    }

    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(studentDto.getName());
            return mapToDto(studentRepository.save(student));
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDto mapToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        return dto;
    }
}
