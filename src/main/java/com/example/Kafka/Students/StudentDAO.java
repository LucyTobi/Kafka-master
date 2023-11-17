package com.example.Kafka.Students;

import java.util.List;

public interface StudentDAO {
    boolean saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentByID(Long id);

    boolean deleteStudentByID(Long id);

    Student getStudentsByName(String name);
}
