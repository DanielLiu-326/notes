package com.example.demo2.service.impl;

import com.example.demo2.dao.StudentMapper;
import com.example.demo2.model.Student;
import com.example.demo2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Override
    public Student getStudentById(int id) {
        return this.studentMapper.selectByPrimaryKey(id);
    }
}
