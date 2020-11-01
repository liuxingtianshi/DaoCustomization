package com.zs.framework.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {
    /**
     * @param id    通过id删除对应的数据
     * @param clazz 通过反射拿到要删除数据的表名
     */
    void deleteById(Integer id, Class<?> clazz);


    /**
     * @param t 要删除的对象，可通过对象中的id进行删除数据
     *          通过反射拿到要删除数据的表名
     */

    <T> void remove(T t);


    /**
     * @param id    通过id查询对应的数据
     * @param clazz
     * @return
     */

    <T> T findById(Integer id, Class<?> clazz);


    /**
     * @param clazz 查询对应表中所有的数据
     * @return
     */
    <T> List<T> findAll(Class<?> clazz);


    /**
     * 根据用户输入的SQL语句去查询数据
     *
     * @param clazz
     * @param sql
     * @param objects
     * @return
     */

    <T> List<T> query(Class<?> clazz, String sql, Object... objects);


    /**
     * 用户传入对象，根据对象找到对应要保存的表
     * insert into tableName(列名，列名，列名，列名)  values(?,?,?,?)
     *
     * @param t
     * @return
     */
    <T> Serializable save(T t);


    /**
     * 更新方法，根据id更新数据
     *
     * @param t
     */
    <T> void update(T t);

}
