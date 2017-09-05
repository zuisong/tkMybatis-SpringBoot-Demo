package cn.mmooo.service;

import cn.mmooo.AppTest;
import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends AppTest {

    @Autowired
    private UserService userService;


    @Test
    public void deleteUserById() throws Exception {
        int i = userService.deleteUserById(1);
       // Assert.assertEquaplls(1, i);
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setName("chen");
        userService.getUser(user);
    }

    @Test
    public void selectAllUser() throws Exception {
        List<User> users = userService.selectAllUser();
        System.out.println(users);
    }

    @Test
    public void selecUser() throws Exception {
        User user = userService.getUserById(1);
        System.out.println(user);
    }

}
