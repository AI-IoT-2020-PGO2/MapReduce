package com.github.wouterreijgers.map_reduce.database;

import java.util.List;

public class UserActivityRead {
    public List<String> userids;

    public UserActivityRead(List<String> userids){
        this.userids = userids;
    }

    public List<String> getUserids() {
        return userids;
    }

}
