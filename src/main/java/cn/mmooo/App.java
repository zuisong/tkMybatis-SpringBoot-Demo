package cn.mmooo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.mmooo.dao.UserMapper;
import cn.mmooo.model.User;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackageClasses = UserMapper.class)
public class App {
	@Autowired
	UserMapper mapper;

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
		UserMapper mapper = applicationContext.getBean(UserMapper.class);
	
		
		
		PageHelper.startPage(100, 10);
		List<User> list = mapper.selectAll();
		
		if (list instanceof Page) {
			Page<User> selectAll = (Page<User>) list;
			list = selectAll.getResult();
			System.out.println("=========进入分页=======");
			
		}
		
		
		list.stream().forEach(System.out::println);
		// System.out.println(workerMapper.selectAll());
	}

	// @Scheduled(cron = "*/1 * * * * ?")
	public void test1() {
		log.info("任务执行,{}", new Date().toString());
		User user = new User();
		user.setName("陈健");
		mapper.insertSelective(user);
	}

}
