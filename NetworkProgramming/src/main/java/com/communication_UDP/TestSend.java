package com.communication_UDP;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class TestSend {

    //发送方
    public static void main(String[] args){
        System.out.println("学生上线了！");
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(8888);
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("学生:");
                String message_1 = scanner.next();

                byte[] bytes_1 = message_1.getBytes();
                DatagramPacket datagramPacket_1 = new DatagramPacket(bytes_1, bytes_1.length, InetAddress.getByName("localhost"), 9999);
                datagramSocket.send(datagramPacket_1);

                if ("byebye".equals(message_1)) {
                    System.out.println("学生下线！！");
                    break;
                }

                byte[] bytes_2 = new byte[1024];
                DatagramPacket datagramPacket_2 = new DatagramPacket(bytes_2, bytes_2.length);
                datagramSocket.receive(datagramPacket_2);
                String message_2 = new String(datagramPacket_2.getData(), 0, datagramPacket_2.getLength());
                System.out.println("老师对我说：" + message_2);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }
}
