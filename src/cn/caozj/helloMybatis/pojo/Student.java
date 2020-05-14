package cn.caozj.helloMybatis.pojo;

public class Student {
    int id;
    int studentID;
    String name;

    public int getId() {
        return id;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getId() + '-' + this.getName();
    }
}
