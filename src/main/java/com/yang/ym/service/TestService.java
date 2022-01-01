package com.yang.ym.service;

import com.yang.ym.dao.StudentDao;
import com.yang.ym.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author qcy
 * @create 2021/11/29 22:53:45
 */
@Service
public class TestService {

    @Autowired
    TestService testService;

    @Transactional
    public void saveA() {
        Student a = new Student(null, "a", 18);
        studentDao.insert(a);

        saveB();
        int i = 1 / 0;
    }

    @Transactional
    public void saveC() {
        Student a = new Student(null, "a", 18);
        studentDao.insert(a);

        new Thread(() -> {
            Student b = new Student(null, "b", 18);
            studentDao.insert(b);
        }).start();

        int i = 1 / 0;
    }

    //@Transactional
    public void saveB() {
        Student a = new Student(null, "a", 18);
        studentDao.insert(a);

    }

    @Resource
    StudentService studentService;
    @Resource
    StudentDao studentDao;

    @Transactional
    public void save() {
        studentService.saveA();
        studentService.saveB();
    }

}
