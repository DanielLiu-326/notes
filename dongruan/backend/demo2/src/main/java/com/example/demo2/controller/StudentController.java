package com.example.demo2.controller;

import com.example.demo2.model.Student;
import com.example.demo2.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/getStudents")
    public String getStudents(int id){
        Student student = studentService.getStudentById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("test");
        try {
            return objectMapper.writeValueAsString(student);
        }catch (Exception e){
            return null;
        }
    }
}
