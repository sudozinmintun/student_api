package com.student.student.service;


import com.student.student.dto.CourseDto;
import com.student.student.dto.TeacherDto;
import com.student.student.model.Course;
import com.student.student.model.Teacher;
import com.student.student.repository.CourseRepository;
import com.student.student.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository; // Inject the TeacherRepository

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(this::mapToDto).orElse(null);
    }

    public CourseDto createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());

        // Find the teacher by ID
        Optional<Teacher> teacher = teacherRepository.findById(courseDto.getTeacherId());
        teacher.ifPresent(course::setTeacher); // Set the teacher if found

        return mapToDto(courseRepository.save(course));
    }

    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setTitle(courseDto.getTitle());

            // Update teacher
            Optional<Teacher> teacher = teacherRepository.findById(courseDto.getTeacherId());
            teacher.ifPresent(course::setTeacher);

            return mapToDto(courseRepository.save(course));
        }
        return null;
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseDto mapToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        if (course.getTeacher() != null) {
            dto.setTeacherId(course.getTeacher().getId()); // Set teacherId in DTO

            // Map teacher details to TeacherDto
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(course.getTeacher().getId());
            teacherDto.setName(course.getTeacher().getName());
            dto.setTeacher(teacherDto); // Set the teacher DTO
        }
        return dto;
    }
}
