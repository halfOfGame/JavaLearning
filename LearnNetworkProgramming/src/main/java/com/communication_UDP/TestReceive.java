package com.communication_UDP;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class TestReceive {

    //接收方
    public static void main(String[] args){
        System.out.println("老师上线了！");
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(9999);
            while (true) {
                byte[] bytes_1 = new byte[1024];
                DatagramPacket datagramPacket_1 = new DatagramPacket(bytes_1, bytes_1.length);
                datagramSocket.receive(datagramPacket_1);

                String message_1 = new String(datagramPacket_1.getData(),0, datagramPacket_1.getLength());
                System.out.println("学生对我说：" + message_1);
                if ("byebye".equals(message_1)) {
                    System.out.println("老师下线！");
                    break;
                }

                Scanner scanner = new Scanner(System.in);
                System.out.print("老师：");
                String message_2 = scanner.next();
                byte[] bytes_2 = message_2.getBytes();
                DatagramPacket datagramPacket_2 = new DatagramPacket(bytes_2, bytes_2.length, InetAddress.getByName("localhost"),8888);
                datagramSocket.send(datagramPacket_2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert datagramSocket != null;
            datagramSocket.close();
        }
    }
}
