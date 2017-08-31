package cn.mmooo.service;

import cn.mmooo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test

    public void deleteUserById() throws Exception {
        userService.deleteUserById(1);
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setName("chen");
        userService.insertUser(user);
    }

    @Test
    public void selectAllUser() throws Exception {
        userService.selectAllUser();
    }
    @Test
    public void selectAllUser2() throws Exception {
        userService.selectAllUser();
    }

}
