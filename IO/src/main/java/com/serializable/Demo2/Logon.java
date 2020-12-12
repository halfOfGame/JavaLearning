package com.serializable.Demo2;

import java.util.concurrent.*;
import java.io.*;
import java.util.*;

public class Logon implements Serializable {
    private Date date = new Date();
    private String username;
    private transient String password;
    public Logon(String name, String pwd) {
        username = name;
        password = pwd;
    }
    @Override
    public String toString() {
        return "logon info: \n username: " +
                username + "\n date: " + date +
                "\n password: " + password;
    }
    public static void main(String[] args) {
        Logon a = new Logon("Hulk", "myLittlePony");
        System.out.println("logon a = " + a);
        try(
                ObjectOutputStream o =
                        new ObjectOutputStream(
                                new FileOutputStream("./files/Logon.dat"))
        ) {
            o.writeObject(a);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
// Now get them back:
        try(
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream("./files/Logon.dat"))
        ) {
            System.out.println(
                    "Recovering object at " + new Date());
            a = (Logon)in.readObject();
        } catch(IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("logon a = " + a);
        System.out.println("password未参与序列化！！");
    }
}