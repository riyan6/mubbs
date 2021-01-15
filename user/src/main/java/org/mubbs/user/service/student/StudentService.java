package org.mubbs.user.service.student;

import org.mubbs.user.dao.student.StudentMapper;
import org.mubbs.user.domain.entity.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author cpz
 * @Date 2021/1/15
 * @Description:
 */
@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public List<Student> getAll() {
        return studentMapper.selectAll();
    }

}
