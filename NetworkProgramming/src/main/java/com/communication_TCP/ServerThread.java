package com.communication_TCP;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket = null;
    InputStream inputStream = null;
    ObjectInputStream objectInputStream = null;
    OutputStream outputStream = null;
    DataOutputStream dataOutputStream = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            User user = (User) (objectInputStream.readObject());
            String loginMessage = null;
            if ("jack".equals(user.getUserName()) && "root".equals(user.getPassword()))
                loginMessage = "登录成功！！";
            else
                loginMessage = "登录失败！！";
            socket.shutdownInput();
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(loginMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null)
                    dataOutputStream.close();
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
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
