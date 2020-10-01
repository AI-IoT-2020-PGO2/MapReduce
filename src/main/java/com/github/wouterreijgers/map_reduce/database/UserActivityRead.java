package com.github.wouterreijgers.map_reduce.database;

import java.io.*;
import java.util.List;

public class UserActivityRead {
    public List<String> userids;

    public UserActivityRead(List<String> userids){
        this.userids = userids;
    }

    public List<String> getUserids() {
        return userids;
    }

    public File getFile() {
        try{
            File file = new File("test.txt");
            FileWriter writer = new FileWriter(file);
            for(String user:userids)
                writer.write(user + "\n");
            writer.close();
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
