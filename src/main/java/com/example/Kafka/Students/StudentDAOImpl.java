package com.example.Kafka.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StudentDAOImpl implements StudentDAO{
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "Student";
    @Override
    public boolean saveStudent(Student student) {
        try {
            redisTemplate.opsForHash().put(KEY, student.getId().toString(),student);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList;
        studentList = redisTemplate.opsForHash().values(KEY);
        return  studentList;
    }

    @Override
    public Student getStudentByID(Long id) {
        Student student;
        student = (Student)redisTemplate.opsForHash().get(KEY,id.toString());
        return student;
    }

    @Override
    public boolean deleteStudentByID(Long id) {
        try {
            Student student =  (Student)redisTemplate.opsForHash().get(KEY,id.toString());
            if (student != null){
                redisTemplate.opsForHash().delete(KEY, id.toString());
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student getStudentsByName(String name) {
        Student student;
        student = (Student)redisTemplate.opsForHash().get(KEY,name);
        return student;
    }
}
