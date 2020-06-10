package cn.caozj.sorm.po;

import java.sql.*;
import java.util.*;

public class Student{
		private Integer studentID;

		private String name;

		private Integer id;

		public Integer getStudentID(){
			return this.studentID;
		}

		public String getName(){
			return this.name;
		}

		public Integer getId(){
			return this.id;
		}

		public void setStudentID(Integer studentID){
			this.studentID = studentID;
		}

		public void setName(String name){
			this.name = name;
		}

		public void setId(Integer id){
			this.id = id;
		}

}
