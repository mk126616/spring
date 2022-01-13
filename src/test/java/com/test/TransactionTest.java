package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring-mvc.xml"))
public class TransactionTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("myDataSource")
    private DataSource mydataSource;

    @Test
    public void rollbackTest() {
        System.out.println("事务回滚测试");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    @Test
    public void jdbcTest() {
        Properties properties = new Properties();
        Connection connection = null;
        try {
            properties.load(TransactionTest.class.getClassLoader().getResource("jdbc.properties").openStream());
            connection = DriverManager.getConnection(properties.getProperty("jdbc.properties.url"), properties.getProperty("jdbc.properties.username"), properties.getProperty("jdbc.properties.driverClassName"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Test
    public void myDataSourceTest() {

        Connection connection = null;
        try {
            connection = mydataSource.getConnection();
            String sql = "insert into user_info (id,username,age) values(1,'张三',20)";
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            int a = 1 / 0;
        } catch (Exception throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {

            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
