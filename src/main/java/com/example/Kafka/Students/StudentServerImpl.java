package com.example.Kafka.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServerImpl implements StudentServer {
    @Autowired
    private StudentDAO studentDAO;
    @Override
    public boolean saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public Student getStudentsByID(Long id) {
        return  studentDAO.getStudentByID(id);
    }

    @Override
    public boolean deleteStudentByID(Long id) {
        return studentDAO.deleteStudentByID(id);
    }

    @Override
    public Student getStudentsByName(String name) {
        return  studentDAO.getStudentsByName(name);
    }


}
