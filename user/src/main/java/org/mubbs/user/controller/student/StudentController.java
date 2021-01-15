package org.mubbs.user.controller.student;

import org.mubbs.user.domain.entity.student.Student;
import org.mubbs.user.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author cpz
 * @Date 2021/1/15
 * @Description:
 */
@RestController
@RequestMapping("/user/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> list() {
        return studentService.getAll();
    }

}
