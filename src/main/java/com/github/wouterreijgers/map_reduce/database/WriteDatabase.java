package com.github.wouterreijgers.map_reduce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

public class WriteDatabase {

    private final String url;
    private final String username;
    private final String password;
    public HashSet<Integer> users_added;
    public HashSet<Integer> songs_added;


    public WriteDatabase(String url, String username, String password)
    {
        this.url = url != null ? url : "jdbc:mysql://localhost:3036/club_iot";
        this.username = username != null ? username : "root";
        this.password = password != null ? password : "";
        this.users_added = new HashSet<Integer>();
        this.songs_added = new HashSet<Integer>();

    }

    public void updateSongScore(Map<Integer, Integer> likes, Map<Integer, Integer> dislikes, HashSet<Integer> songList){
        int i = 0;
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(this.url, this.username,this.password);
            System.out.println("Connected");
        }catch (SQLException | ClassNotFoundException e){
            System.err.println(e);
        }

        PreparedStatement preparedStatement = null;
        String updateTableSQL = "UPDATE processed_votes SET total_score =?, total_votes =? WHERE played_song_id =? ";
        try
        {
            if(likes != null){

            }
            for (Integer songID : songList){
                if(!songs_added.contains(songID)){
                    create_song(songID);
                    users_added.add(songID);
                }
                int like = likes.get(songID);
                int dislike = dislikes.get(songID);
                assert (dbConnection != null);
                preparedStatement = dbConnection.prepareStatement(updateTableSQL);
                preparedStatement.setInt(1, like - dislike);
                preparedStatement.setInt(2, like + dislike);
                preparedStatement.setInt(3, songID);
                preparedStatement .executeUpdate();
                i++;
            }
//            dbConnection.commit();
            System.out.println(i + " Records updated!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUsersField(Map<Integer, Integer> users) {
        int i = 0;
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(this.url, this.username,this.password);
            System.out.println("Connected");
        }catch (SQLException | ClassNotFoundException e){
            System.err.println(e);
        }

        PreparedStatement preparedStatement = null;
        String updateTableSQL = "UPDATE users SET total_votes =? WHERE ID =? ";
        try
        {
            for (Map.Entry<Integer, Integer> e : users.entrySet()){
                if(!users_added.contains(e.getKey())){
                    create_user(e.getKey(), e.getValue());
                    users_added.add(e.getKey());
                }
                assert (dbConnection != null);
                preparedStatement = dbConnection.prepareStatement(updateTableSQL);
                preparedStatement.setInt(1, e.getValue());
                preparedStatement.setInt(2, e.getKey());
                preparedStatement .executeUpdate();
                i++;
            }
//            dbConnection.commit();
            System.out.println(i + " Records updated!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create_user(Integer id, Integer votes){
        try
        {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.url, this.username,this.password);
            System.out.println("Connected");

            // the mysql insert statement
            String query = " insert into users (id, total_votes)"
                    + " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setInt (1, id);
            preparedStmt.setInt (2, votes);

            // execute the preparedstatement
            preparedStmt.execute();

            dbConnection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void create_song(Integer id){
        try
        {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.url, this.username,this.password);
            System.out.println("Connected");

            // the mysql insert statement
            String query = " insert into processed_votes (played_song_id, total_score, total_votes)"
                    + " values (?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setInt (1, id);
            preparedStmt.setInt (2, 0);
            preparedStmt.setInt (3, 0);


            // execute the preparedstatement
            preparedStmt.execute();

            dbConnection.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
