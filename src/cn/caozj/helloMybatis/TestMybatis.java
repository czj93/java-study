package cn.caozj.helloMybatis;

import cn.caozj.helloMybatis.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class TestMybatis {

    public static void main(String[] args) throws Exception{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();

        // 获取所有的 student
        List<Student> listStudent = session.selectList("listStudent");
        for (Student student : listStudent) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }

        // 取得 id = 2 的 student
        Student student2 = session.selectOne("getStudent", 2);
        System.out.println(student2);

        // 修改 student2
        student2.setName("caozhijian1");

        session.update("updateStudent", student2);

        // 事务提交
        session.commit();

        if(session != null){
            session.close();
        }

    }
}
