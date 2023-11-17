package com.example.Kafka.controller;

import com.example.Kafka.DTO.InformationDTO;
import com.example.Kafka.Exception.BaseException;
import com.example.Kafka.Manager.RedisManager;
import com.example.Kafka.Students.Student;
import com.example.Kafka.Students.StudentServer;
import org.apache.tomcat.util.http.ResponseUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RedisController {
    private boolean check_student;
    private  String check_exists;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public StudentServer studentServer;

    @PostMapping("/student")
    public ResponseEntity<String> saveStudent(@RequestBody @NotNull Student student) {
        String a = String.valueOf(student.getId());
        fetchStudentByID(a);
        if (check_student == true){
            boolean result = studentServer.saveStudent(student);
            if (result) {
                return ResponseEntity.ok("Student Create Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }else  return ResponseEntity.ok("Student is locked by another user");
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> fetchStudent() {
        List<Student> studentList;
        studentList = studentServer.getAllStudents();
        for(Student student : studentList){
            System.out.println("The value an Email address in Redis :" + student.getEmail());
        }
        return ResponseEntity.ok(studentList);

    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> fetchStudentByID(@PathVariable("id") String id) {
        Long ID = Long.valueOf(id);
        Student student;
        student = studentServer.getStudentsByID(ID);
        if (student == null){
            check_student = true;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            check_student = false;
            System.out.println("Value in Redis hash:" + student.getEmail());
            return ResponseEntity.ok(student);
        }
    }
    @GetMapping("/api/student/{name}")
    public ResponseEntity<Student> fetchStudentByName(@PathVariable("name") String name) {
        Student student;
        student = studentServer.getStudentsByName(name);
        if (student == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            System.out.println("Value in Redis hash:" + student.getName());
            return ResponseEntity.ok(student);
        }
    }

    @DeleteMapping("/delStudent/{id}")
    public ResponseEntity<String> deleteStudentByID(@PathVariable("id") Long id) {
        boolean result = studentServer.deleteStudentByID(id);
        if (result) {
            return ResponseEntity.ok("Student Delete Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = {"/postStudent"})
    public ResponseEntity<String> getStudent(@RequestBody Student  mStudent) throws BaseException {
        log.info("REST request to get Student : {}", mStudent);

        if (mStudent == null) {
            throw new BaseException("SYS_00011");
        }
        try {
            // Test Redis cache
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put("id", mStudent.getId());
            map.put("name",  mStudent.getName());
            map.put("email",  mStudent.getEmail());
            map.put("age", mStudent.getAge());
            // Set data into hash

            RedisManager redis = RedisManager.getInstance();
            check_exists =  (String)redis.getObjectInHash("Student", "id");
            redis.setHashAll("Student", map, false);
            // Get data into hash
            Long val =  (Long)redis.getObjectInHash("Student", "id");
            System.out.println("Value in Redis hash:" + map);
            // Get hash in red
            map = redis.getHash("Student");
            List<Object> result = map.values().stream().collect(Collectors.toList());
            System.out.println("Values in list:" + result);

////             Update data in hash
//            redis.setObjectInHash("Student", "id", "3", false);
//            System.out.println("Value in Redis hash after update:" + redis.getObjectInHash("Student", "id"));
//            // Delete data in hash
//            check_exists =  (String)redis.getObjectInHash("Student", "id");
//            redis.clearObjectInHash("Student", "id");
//            System.out.println("Value in Redis hash after delete:" + map.get("id"));
//             check_exists =  (String)redis.getObjectInHash("student", "id");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Post Successfully");
    }
}
