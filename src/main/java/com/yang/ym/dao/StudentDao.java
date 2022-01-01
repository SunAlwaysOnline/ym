package com.yang.ym.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.ym.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qcy
 * @create 2021/11/28 20:23:54
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {
}
