package cn.mmooo.aop;

import cn.mmooo.config.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


/**
 * 在service层觉得数据源
 * <p>
 * 必须在事务AOP之前执行，所以实现Ordered,order的值越小，越先执行
 * 如果一旦开始切换到写库，则之后的读都会走写库
 */
@Aspect
@Component
@Slf4j
public class DataSourceAopInService implements PriorityOrdered {

    private Boolean isReadMethod(String methodName) {
        // 方法名以query、find、get、select开头的方法名走从库
        return StringUtils.startsWithAny(methodName.toLowerCase(), new String[]{"query", "find", "get", "select"});
    }

    @Pointcut("execution(* cn.mmooo.service..*.*(..)) ")
    public void excudeService() {

    }

    @After("excudeService()")
    public void clearDataSourceType() {
        log.debug("清除已经设置的数据类型，方便下次使用");
        DynamicDataSourceHolder.clear();
    }

    @Before("excudeService()")
    public void setDataSourceType(JoinPoint joinPoint) {

        final String methodName = joinPoint.getSignature().getName();
        if (isReadMethod(methodName)) {
            log.debug("动态切换数据库===切换到数据库==");
            DynamicDataSourceHolder.routeSlave();
        } else {
            log.debug("动态切换数据库===切换到写数据库==");
            DynamicDataSourceHolder.routeMaster();

        }
    }

    @Override
    public int getOrder() {
        /**
         * 值越小，越优先执行
         * 要优于事务的执行
         * 在启动类中加上了@EnableTransactionManagement(order = 10)
         */
        return 1;
    }

}
