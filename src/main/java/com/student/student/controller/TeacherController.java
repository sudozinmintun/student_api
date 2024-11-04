package com.student.student.controller;

import com.student.student.dto.TeacherDto;
import com.student.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        TeacherDto teacherDto = teacherService.getTeacherById(id);
        return teacherDto != null ? ResponseEntity.ok(teacherDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherDto createdTeacher = teacherService.createTeacher(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        TeacherDto updatedTeacher = teacherService.updateTeacher(id, teacherDto);
        return updatedTeacher != null ? ResponseEntity.ok(updatedTeacher) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
