package com.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        //封装IP
        InetAddress inetAddress = InetAddress.getByName("localhost");
        System.out.println(inetAddress);
        System.out.println(inetAddress.getAddress());
        System.out.println(inetAddress.getHostAddress());
    }
}
