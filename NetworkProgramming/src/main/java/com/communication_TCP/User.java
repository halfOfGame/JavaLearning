package com.communication_TCP;

import java.io.Serializable;

public class User implements Serializable {
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    String userName;
    String password;
}
