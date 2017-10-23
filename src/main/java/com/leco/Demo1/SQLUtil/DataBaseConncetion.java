package com.leco.Demo1.SQLUtil;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库连接池
 */
public class DataBaseConncetion {
    //数据库配置资源
    private static DruidDataSource dataSource;

    static {
        //获取资源文件
        InputStream in = DataBaseConncetion.class.getClassLoader().getResourceAsStream("db.properties");
        Properties  proper = new Properties();
        try {
            //加载
            proper.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(proper.getProperty("driver"));
        dataSource.setUrl(proper.getProperty("url"));
        dataSource.setUsername(proper.getProperty("name"));
        dataSource.setPassword(proper.getProperty("password"));

    }


    public Connection getConnection() throws SQLException {
        if (dataSource==null)return null;
             return dataSource.getConnection();
    }


    @Test
     public void test() throws SQLException {
        Connection conn = getConnection();
        if (conn!=null)
            System.out.println(1);
        else
            System.out.println(0);

        Statement statement = conn.createStatement();


//        statement.executeUpdate("INSERT User VALUES('root',888888,'root','22')");
        ResultSet rs = statement.executeQuery("SELECT * FROM User");
        if (rs!=null){
            while (rs.next()){
                System.out.println(" "+rs.getString("id"));
                System.out.println(" "+rs.getString("password"));
            }
        }
    }

}
