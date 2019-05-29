package com.it18zhang.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 接受方
 */
public class UdpReceiver {
	public static void main(String[] args) throws Exception {
		DatagramSocket sock = new DatagramSocket(8888);
		byte[] buf = new byte[1024];
		DatagramPacket pack = new DatagramPacket(buf, buf.length);
		while(true){
			sock.receive(pack);
			int dataLen = pack.getLength();
			String str = new String(buf,0,dataLen);
			System.out.println("收到了 : " + str);
		}
	}
}
