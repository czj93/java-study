package cn.caozj.spring.bean;

import org.springframework.stereotype.Component;

@Component
public class Car {
    public void start(){
        System.out.println("car 开动");
    }
}
