package cn.caozj.learn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public class Reflect {

    public static void main(String[] args){
        try {
            // 获取一个 Class
            Class clz = Class.forName("cn.caozj.learn.User");
            System.out.println(clz.getName()); // 获取包名加类名  cn.caozj.learn.User
            System.out.println(clz.getSimpleName()); // 获取类名


            // 获取属性
            Field[] allFields = clz.getDeclaredFields();        // 获取所有的属性
            Field[] fields = clz.getFields();  // 获取class 属性。 只能获取 public 的属性
            Field ageField = clz.getDeclaredField("age");        // 获取指定的属性

            for (Field field:fields){
                System.out.println(field);
            }
            // 获取方法
            Method[] methods = clz.getMethods();
            Method[] allMethods = clz.getDeclaredMethods();
            // 获取指定的方法 除了要传方法名外， 还要传方法的类型， 主要是为了区分 重载的方法
            Method setAge = clz.getDeclaredMethod("setAge", int.class);

            // 获取构造器
            Constructor[] constructors = clz.getDeclaredConstructors();
            //获取指定的构造器
            Constructor constructor = clz.getDeclaredConstructor(); // 无参构造器


            // 调用反射api , 创建对象
            User user = (User) clz.getDeclaredConstructor().newInstance(); // 获得无参构造器， 新建一个对象

            User caozhijan = (User) clz.getDeclaredConstructor(String.class, int.class, String.class)
                    .newInstance("caozhijan", 18, "man"); // 调用一个有参构造器， 创建一个对象

            // 通过反射调用 方法
            setAge.invoke(caozhijan, 17);       // 调用 caozhijan 对象，设置 age
            System.out.println("caozhijian age:" + caozhijan.getAge());


            // 通过反射给对象设置属性

            // 禁止安全检查可以提高性能
            ageField.setAccessible(true); // 将是由属性设置 允许访问
            ageField.setInt(caozhijan, 18);
            System.out.println("caozhijian age:" + caozhijan.getAge());


            // 获取class注解
            Annotation[] annotations = clz.getAnnotations();
            // 获取特定的注解
            // Webservlet webservlet = (WebServerlt) clz.getAnnotation(WebServlet.class)
            // webservlet.value()   // 获取注解的 value  值

            // 获取属性的注解
            //  1. 先获得属
            //  2. 再从属性上获得注解
            // ageField.getAnnotation()


            // 通过反射调用 方法 会比普通的方式慢， 禁止安全检查可以提高性能

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
