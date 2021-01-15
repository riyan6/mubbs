package org.mubbs.user.domain.entity.student;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "t_student")
public class Student {

    @Id
    @Column(name = "stu_id")
    private Integer stuId;

    /**
     * 学生名
     */
    @Column(name = "stu_name")
    private String stuName;

    /**
     * 学生年龄
     */
    @Column(name = "stu_age")
    private Integer stuAge;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;
}