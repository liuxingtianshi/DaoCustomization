package com.zs.test;

import com.zs.framework.bean.Animal;
import com.zs.framework.bean.User;
import com.zs.framework.dao.BaseDao;
import com.zs.framework.dao.BaseDaoImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

public class BaseDaoImplTest {

    private static BaseDao baseDao = new BaseDaoImpl();
    private static Class<User> userClazz = User.class;
    private static Class<Animal> animalClazz = Animal.class;

    @Test
    public void deleteById() {
        baseDao.deleteById(9, animalClazz);
        Assert.assertNull(baseDao.findById(9, animalClazz));

        baseDao.deleteById(7, userClazz);
        Assert.assertNull(baseDao.findById(7, userClazz));
    }

    @Test
    public void remove() {
        Animal animal = new Animal(10, "狼", "公", 5, null);
        baseDao.remove(animal);

        User user = new User(8, "张帅", "男", 30, null);
        baseDao.remove(user);
    }

    @Test
    public void findById() {
        User user = baseDao.findById(1, userClazz);
        System.out.println(user);
        Assert.assertNotNull(user);

        Animal animal = baseDao.findById(1, animalClazz);
        System.out.println(animal);
        Assert.assertNotNull(animal);
    }

    @Test
    public void findAll() {
        List<User> userList = baseDao.findAll(userClazz);
        for (User user : userList) {
            System.out.println(user);
        }

        List<Animal> animalList = baseDao.findAll(animalClazz);
        for (Animal animal : animalList) {
            System.out.println(animal);
        }
    }

    @Test
    public void query() {
        String sql = "select * from tb_user where name = ?  and age = ?";
        Object[] params = new Object[]{"李四", 18};
        List<User> userList = baseDao.query(userClazz, sql, params);
        for (User user : userList) {
            System.out.println(user);
        }

        Assert.assertNotNull(userList);
    }

    @Test
    public void save() {
        Animal animal = new Animal("狼", "公", 5, null);
        Serializable serializable = baseDao.save(animal);
        System.out.println(serializable);

        User user = new User("张帅", "男", 30, null);
        Serializable serializable1 = baseDao.save(user);
        System.out.println(serializable1);
    }

    @Test
    public void update() {
        Animal animal = new Animal(9, "大灰狼", "母", 2, null);
        baseDao.update(animal);
        User user = new User(7, "帅女", "女", 25, null);
        baseDao.update(user);
    }
}