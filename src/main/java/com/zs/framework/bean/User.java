package com.zs.framework.bean;

import java.util.Date;
import java.util.Objects;

import com.zs.framework.annotation.AnnotationClass;
import com.zs.framework.annotation.AnnotationField;


@AnnotationClass("tb_user")
public class User {
	private Integer id;
	private String name;//setName
	private String sex;//setSex
	private Integer age;
	@AnnotationField("create_date")
	private Date createDate;
	
	public User() {
		System.out.println("===========user无参构造器===========");
	}
	
	
	public User(String name, String sex, Integer age, Date createDate) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.createDate = createDate;
	}


	public User(Integer id, String name, String sex, Integer age, Date createDate) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.createDate = createDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", createDate=" + createDate
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getId().equals(user.getId()) &&
				getName().equals(user.getName()) &&
				getSex().equals(user.getSex()) &&
				getAge().equals(user.getAge()) &&
				getCreateDate().equals(user.getCreateDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getSex(), getAge(), getCreateDate());
	}
}
