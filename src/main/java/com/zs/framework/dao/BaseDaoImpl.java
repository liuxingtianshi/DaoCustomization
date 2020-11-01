package com.zs.framework.dao;

import com.zs.framework.util.CommonUtils;
import com.zs.framework.util.ConectionFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoImpl implements BaseDao {

    public void deleteById(Integer id, Class<?> clazz) {
        //delete from 表名 where id = ?

        //根据类对象获取表名
        String tableName = CommonUtils.getTableName(clazz);

        //拼接SQL
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName);
        sql.append(" where id = ?");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            //设置id参数并执行
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
    }

    public <T> void remove(T t) {
        // delete from 表名 where id = ?
        // 根据对象获取表名
        String tableName = CommonUtils.getTableNameByObject(t);
        //拼接SQL
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName);
        sql.append(" where id = ?");

        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            Method method = t.getClass().getDeclaredMethod("getId");
            Object id = method.invoke(t);
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
    }


    public <T> T findById(Integer id, Class<?> clazz) {
        // select * from 表名 where id = ?
        // 获取表名
        String tableName = CommonUtils.getTableName(clazz);
        // 拼接SQL
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(tableName);
        sql.append(" where id = ?");

        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setObject(1, id);
            preparedStatement.execute();

            //获取结果集并把唯一的结果封装到对象中返回
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()) {
                //创建一个对象用于封装查询结果
                Object object = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    //不知道列名，先通过成员变量名反射出上面的注解值获取列名
                    String columnName = CommonUtils.getColumnNameByField(field);
                    //获取列值
                    Object value = rs.getObject(columnName);
                    //获取setter方法并调用赋值
                    String setterMethodStr = CommonUtils.getMethodName(field.getName(), "set");
                    Method setterMethod = clazz.getDeclaredMethod(setterMethodStr, field.getType());
                    setterMethod.invoke(object, value);
                }
                // 前提是知道id唯一，所以只返回第一条
                return (T) object;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
        return null;
    }

    public <T> List<T> findAll(Class<?> clazz) {
        // select * from 表名
        String tableName = CommonUtils.getTableName(clazz);
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(tableName);
        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.execute();

            List<T> resultList = new ArrayList<T>();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()) {
                T object = (T) clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    // 获取列名, 获取列值
                    String columnName = CommonUtils.getColumnNameByField(field);
                    Object columnValue = rs.getObject(columnName);

                    // 获取setter方法
                    String setterName = CommonUtils.getMethodName(field.getName(), "set");
                    Method method = clazz.getDeclaredMethod(setterName, field.getType());
                    method.invoke(object, columnValue);
                }
                resultList.add(object);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
        return null;
    }

    /**
     * select * from tb_user where name = ?  and age = ?", new Object[] {"张三",28}
     *
     * @param clazz
     * @param sql
     * @param objects
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<?> clazz, String sql, Object... objects) {
        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            int count = 1;
            for (Object object : objects) {
                // count ++ 先执行后加不影响当前循环
                preparedStatement.setObject(count++, object);
            }
            preparedStatement.execute();

            // 存放结果集
            List<T> resultList = new ArrayList<T>();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()) {
                T object = (T) clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    // 获取列名, 获取列值
                    String columnName = CommonUtils.getColumnNameByField(field);
                    Object columnValue = rs.getObject(columnName);

                    // 获取setter方法
                    String setterName = CommonUtils.getMethodName(field.getName(), "set");
                    Method method = clazz.getDeclaredMethod(setterName, field.getType());
                    method.invoke(object, columnValue);
                }
                resultList.add(object);
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
        return null;
    }

    /**
     * insert into 表名 ('列名','列名','列名') values (?，?，?)
     * id 自增，不需要赋值
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> Serializable save(T t) {
        String tableName = CommonUtils.getTableNameByObject(t);
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append(tableName);
        sql.append(" (");

        // 获取所有列名(除了id)
        List<String> columnNames = CommonUtils.getAllColumnNamesByObject(t);
        for (String column : columnNames) {
            sql.append(column + ",");
        }
        // 去掉逗号
        sql = CommonUtils.removeLastComma(sql);
        sql.append(") values (");
        for (String column : columnNames) {
            sql.append("?" + ",");
        }
        sql = CommonUtils.removeLastComma(sql);
        sql.append(")");

        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());

            List<Object> fieldValues = CommonUtils.getFieldValues(t);
            int count = 1;
            for (Object value : fieldValues) {
                preparedStatement.setObject(count++, value);
            }

            // 执行SQL语句,返回影响行的id
            int num = preparedStatement.executeUpdate();
            if (num > 0) {
                rs = preparedStatement.getGeneratedKeys();
                rs.next();
                Object id = rs.getObject(1);
                return (Serializable) id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
        return null;
    }

    /**
     * update 表名 set 列名 =? ,列名= ?   where id = ?
     *
     * @param t
     * @param <T>
     */
    public <T> void update(T t) {
        String tableName = CommonUtils.getTableNameByObject(t);
        List<String> columnNames = CommonUtils.getAllColumnNamesByObject(t);
        StringBuffer sql = new StringBuffer("update ");
        sql.append(tableName);
        sql.append(" set ");
        for (String column : columnNames) {
            sql.append(column + "=?,");
        }
        sql = CommonUtils.removeLastComma(sql);
        sql.append(" where id = ?");

        // 获取连接,执行SQL
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());

            List<Object> fieldValues = CommonUtils.getFieldValues(t);
            int count = 1;
            for (Object value : fieldValues) {
                preparedStatement.setObject(count++, value);
            }

            // 获取id并设置参数
            Method getterMethod = t.getClass().getDeclaredMethod("getId");
            Object id = getterMethod.invoke(t);
            preparedStatement.setObject(count, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConectionFactory.close(connection, preparedStatement, null);
        }
    }
}
