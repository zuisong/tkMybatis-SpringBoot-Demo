package cn.mmooo.dao;

import java.util.List;

import cn.mmooo.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    List<User> findByName(@Param("name") String name);
}