package cn.mmooo.handler;

import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class UserHandler {


    @Autowired
    private UserMapper userMapper;


    @ResponseBody
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id) {

        User u = userMapper.selectByPrimaryKey(id);
        System.out.println(u);

        return u;
    }

}
