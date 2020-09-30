package com.communication_TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//服务器端
public class TestServer {
    public static void main(String[] args) {
        System.out.println("---服务器端启动---");
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                //一直接收，并且每接收一个，都有一个线程来处理
                socket = serverSocket.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
