package com.example.demo2.service;

import com.example.demo2.model.Student;
import org.springframework.stereotype.Service;

public interface StudentService {
    Student getStudentById(int id);
}
