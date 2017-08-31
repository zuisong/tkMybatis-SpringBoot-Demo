package cn.mmooo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;

@Controller
public class UserHandler {

    Logger log = LoggerFactory.getLogger(UserHandler.class);

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
