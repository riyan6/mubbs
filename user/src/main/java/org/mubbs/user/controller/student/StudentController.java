package org.mubbs.user.controller.student;

import org.mubbs.user.domain.entity.student.Student;
import org.mubbs.user.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author cpz
 * @Date 2021/1/15
 * @Description:
 */
@RestController
@RequestMapping("/user/student")
public class StudentController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> list() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Map<String, Object> one(@PathVariable("id") int studentId) {
        Map<String, Object> map = new ConcurrentHashMap<>(4);
        Student student = studentService.getOneById(studentId);
        map.put("msg", "by：" + this.serverPort);
        map.put("data", student);
        return map;
    }

}
