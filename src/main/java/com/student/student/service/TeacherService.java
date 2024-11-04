package com.student.student.service;

import com.student.student.dto.CourseDto;
import com.student.student.dto.TeacherDto;
import com.student.student.model.Teacher;
import com.student.student.repository.CourseRepository;
import com.student.student.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Include the CourseRepository to access course data
    @Autowired
    private CourseRepository courseRepository;

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TeacherDto getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.map(this::mapToDto).orElse(null);
    }

    public TeacherDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());

        Teacher savedTeacher = teacherRepository.save(teacher);

        TeacherDto savedTeacherDto = new TeacherDto();
        savedTeacherDto.setName(savedTeacher.getName());
        savedTeacherDto.setId(savedTeacher.getId());

        return savedTeacherDto;
    }

    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        Optional<Teacher> existingTeacherOpt = teacherRepository.findById(id);
        if (existingTeacherOpt.isPresent()) {
            Teacher existingTeacher = existingTeacherOpt.get();
            existingTeacher.setName(teacherDto.getName());

            Teacher updatedTeacher = teacherRepository.save(existingTeacher);
            return mapToDto(updatedTeacher);
        }
        return null;
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    private TeacherDto mapToDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());

        // Assuming the teacher has a method to get the list of courses they teach
        List<CourseDto> courses = teacher.getCourses().stream()
                .map(course -> {
                    CourseDto courseDto = new CourseDto();
                    courseDto.setId(course.getId());
                    courseDto.setTitle(course.getTitle());
                    courseDto.setTeacherId(course.getTeacher().getId()); // Assuming a getTeacher() method in Course
                    return courseDto;
                })
                .collect(Collectors.toList());
        dto.setCourses(courses);

        return dto;
    }
}
