package com.github.wouterreijgers.map_reduce.database;

import java.io.*;
import java.util.List;

public class UserActivityRead {
    public List<Integer> userids;

    public UserActivityRead(List<Integer> userids){
        this.userids = userids;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public File getFile() {
        try{
            File file = new File("usersIn.txt");
            FileWriter writer = new FileWriter(file);
            for(int user:userids)
                writer.write(user + "\n");
            writer.close();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
