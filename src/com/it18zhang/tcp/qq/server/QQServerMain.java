package com.it18zhang.tcp.qq.server;

import java.net.ServerSocket;
import java.net.Socket;

public class QQServerMain {
	
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true){
				//Î¬»¤
				Socket sock = ss.accept();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
