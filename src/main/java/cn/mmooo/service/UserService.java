package cn.mmooo.service;

import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    int deleteUserById(Integer id) {
        log.info("log 已经生效");
        return userMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    int getUser(User user) {
        return userMapper.insert(user);
    }

    List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

}
