package com.communication_TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//客户端
public class TestClient {
    public static void main(String[] args) {
        System.out.println("---客户端启动---");

        Socket socket = null;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;


        try {
            socket = new Socket("localhost", 8888);
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入用户名：");
            String userName = scanner.next();
            System.out.print("请输入密码：");
            String password = scanner.next();
            User user = new User(userName, password);

            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);

            socket.shutdownOutput();
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            System.out.println(dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
