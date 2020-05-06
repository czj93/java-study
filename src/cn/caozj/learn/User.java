package cn.caozj.learn;

public class User {
    private String name;
    private int age;

    public String sex;

    public User(){

    }

    public User(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
