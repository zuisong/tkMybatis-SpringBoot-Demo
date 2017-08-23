package cn.mmooo.dao;


import cn.mmooo.AppTest;
import cn.mmooo.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMapperTest extends AppTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() {
        List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);
    }
}
