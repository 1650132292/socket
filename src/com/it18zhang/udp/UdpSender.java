package com.it18zhang.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 发送方
 */
public class UdpSender {
	public static void main(String[] args) throws Exception {
		DatagramSocket socket = new DatagramSocket(9999);
		
		int i= 0 ;
		for(;;){
			//构造数据缓冲数组，形成数据报包
			byte[] bytes = ("hello world " + i ++).getBytes() ;
			//byte[] bytes = new byte[1024 * 63];
			DatagramPacket pack = new DatagramPacket(bytes, bytes.length);
			pack.setSocketAddress(new InetSocketAddress("192.168.12.255", 8888));
			socket.send(pack);
			Thread.sleep(500);
		}
	}
}
