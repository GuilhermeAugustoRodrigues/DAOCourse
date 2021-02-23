package app;

import db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection connection;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from department");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name"));
            }
        } catch (SQLException reject) {
            reject.printStackTrace();
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
            DB.closeConnection();
        }
        DB.closeConnection();
    }
}
