package com.example.Kafka.Students;

import java.util.List;

public interface StudentServer{
    boolean saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentsByID(Long id);

    boolean deleteStudentByID(Long id);

    Student getStudentsByName(String name);
}
