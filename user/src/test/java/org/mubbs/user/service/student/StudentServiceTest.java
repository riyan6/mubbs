package org.mubbs.user.service.student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mubbs.user.domain.entity.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author cpz
 * @Date 2021/1/15
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void getAll() {
        List<Student> students = studentService.getAll();
        students.stream().forEach(System.out::print);
    }

}