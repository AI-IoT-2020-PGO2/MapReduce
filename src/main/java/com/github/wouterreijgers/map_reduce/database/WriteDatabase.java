package com.github.wouterreijgers.map_reduce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class WriteDatabase {

    public void updateusers(Map<Integer, Integer> users) {
        int i = 0;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String updateTableSQL = "UPDATE users SET total_votes =? WHERE ID =? ";
        try
        {
            //dbConnection = getDBConnection();
            for (Map.Entry<Integer, Integer> e : users.entrySet()){
                preparedStatement = dbConnection.prepareStatement(updateTableSQL);
                preparedStatement.setInt(1, e.getValue());
                preparedStatement.setInt(2, e.getKey());
                preparedStatement .executeUpdate();
                i++;
            }
            dbConnection.commit();
            System.out.println(i + " Records updated!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
