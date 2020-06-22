import cn.caozj.spring.bean.Person;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class iocTest {

    @Test
    public void test(){
        // 根据配置文件获得 容器对象
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-bean-conf.xml");

        Person czj = (Person)ioc.getBean("personczj");
        System.out.println(czj.toString());

        Person czj2 = (Person) ioc.getBean("person2");
        System.out.println(czj2);
    }
}
