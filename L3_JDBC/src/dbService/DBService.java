package dbService;

import accounts.UserProfile;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
    private Connection connection = DBService.getMysqlConnection();

    public void addUser(String login, String password) throws SQLException {
        try {
            this.connection.setAutoCommit(false);
            Statement statement = this.connection.createStatement();
            statement.execute("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id));");
            statement.execute("insert into users (login, password) values ('" + login + "', '" + password + "')");
            this.connection.commit();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUser(String login) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery("select * from users where login='" + login + "'");
        String user_login = "not";
        String user_pass = "found";
        while (result.next()) {
            user_login = result.getString("login");
            user_pass = result.getString("password");
        }
        statement.close();
        return new UserProfile(user_login, user_pass);
    }

    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((java.sql.Driver)((Driver)Class.forName("com.mysql.jdbc.Driver").newInstance()));
            String URL2 = "jdbc:mysql://localhost:3306/mydbtest";
            String USERNAME = "root";
            String PASSWORD = "root";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydbtest", "root", "root");
            return connection;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
