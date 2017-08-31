package cn.mmooo;

import java.util.List;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;

@Rollback
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private DruidDataSource druidDataSource;
    @Autowired
    private UserMapper userMapper;
    //
    // @Test(timeout=100)
    // public void insertTest() {
    //
    // User u = new User();
    // u.setName("测试插入");
    // int i = userMapper.insert(u);
    // assertEquals(1, i);
    // }
    //
    // @Test
    // public void deleteTest() {
    // int i = userMapper.deleteByPrimaryKey(2);
    // assertEquals(1, i);
    // }

    @Test
    public void selectAllTest() {
        // 第二页，每页十条数据
        PageHelper.startPage(2, 10, true);
        List<User> list = userMapper.selectAll();

        if (list instanceof Page) {
            Page<User> selectAll = (Page<User>) list;
            System.out.println(selectAll.getTotal());
            list = selectAll.getResult();
            System.out.println("=========进入分页=======");
        }

        list.stream().forEach(System.out::println);
        // System.out.println(workerMapper.selectAll());
    }


    @Test
    @Rollback(false)
    public void testInsert() {

        User user = new User();
        user.setName("233");
        userMapper.insert(user);


    }

}
