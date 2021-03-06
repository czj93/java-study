package cn.caozj.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {
    private String lastName;
    private Integer age;
    private String gender;
    private String email;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Autowired
    private Car car;

    public Person(){}

    public Person(String lastName, Integer age, String gender, String email) {
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
