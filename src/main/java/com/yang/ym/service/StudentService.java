package com.yang.ym.service;

import com.sun.xml.internal.ws.api.model.CheckedException;
import com.yang.ym.dao.StudentDao;
import com.yang.ym.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileNotFoundException;

/**
 * @author qcy
 * @create 2021/11/28 20:37:24
 */
@Service
public class StudentService {

    @Resource
    StudentDao studentDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save() {
        //会进行回滚
//        Student a = new Student(null, "a", 18);
//        studentDao.insert(a);
//        int i = 1 / 0;
//
//        saveB();

        saveA();
        saveB();


    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveA() {
        Student a = new Student(null, "a", 18);
        studentDao.insert(a);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveB() {
        Student b = new Student(null, "b", 20);
        studentDao.insert(b);
        //模拟业务异常
        int i = 1 / 0;
    }

    @Transactional
    public void save2() throws FileNotFoundException {
        //不会进行回滚
        Student a = new Student(null, "a", 18);
        studentDao.insert(a);
        throw new FileNotFoundException();
    }

    @Transactional
    public void save1() {
        try {
            Student a = new Student(null, "a", 18);
            studentDao.insert(a);

            int i = 1 / 0;
            Student b = new Student(null, "b", 18);
            studentDao.insert(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
