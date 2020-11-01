package com.zs.framework.bean;

import java.util.Date;
import java.util.Objects;

import com.zs.framework.annotation.AnnotationClass;
import com.zs.framework.annotation.AnnotationField;


@AnnotationClass("tb_animal")
public class Animal {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    @AnnotationField("create_date")
    private Date createDate;

    public Animal() {
        System.out.println("=======Animal无参构造器=============");
    }

    public Animal(Integer id, String name, String sex, Integer age, Date createDate) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.createDate = createDate;
    }

    public Animal(String name, String sex, Integer age, Date createDate) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.createDate = createDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Animal [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", createDate=" + createDate
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id) &&
                Objects.equals(name, animal.name) &&
                Objects.equals(sex, animal.sex) &&
                Objects.equals(age, animal.age) &&
                Objects.equals(createDate, animal.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, age, createDate);
    }
}
