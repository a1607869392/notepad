package control;

import java.sql.*;

public class DatabaseConnection {

    public static Connection connect() throws SQLException {
        try {
            // 连接数据库（替换为你的数据库连接信息）
            String url = "jdbc:mysql://localhost:3306/mysql"; // 数据库URL
            String username = "root";  // 数据库用户名
            String password = "a123456789";  // 数据库密码

            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立连接
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}