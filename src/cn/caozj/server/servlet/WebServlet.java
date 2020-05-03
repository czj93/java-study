package cn.caozj.server.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= ElementType.TYPE)            // 注解作用于 类 接口 枚举
@Retention(value= RetentionPolicy.RUNTIME)  // 注解的生命周期 SOURCE/CLASS/RUNTIME = 源码/class文件/运行时
public @interface WebServlet {
    String value();     // 定义一个名为 value 的参数

    // String value() default "";  default 用来设置默认值
    // String[] values() default {"a", "b"};
}
