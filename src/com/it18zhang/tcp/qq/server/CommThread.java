package com.it18zhang.tcp.qq.server;

import java.io.InputStream;
import java.net.Socket;

import com.it18zhang.tcp.qq.common.Message;
import com.it18zhang.tcp.qq.common.MessageFactory;

/**
 * ͨ���߳� 
 */
public class CommThread extends Thread {
	
	//socket
	private Socket sock;
	
	public CommThread(Socket sock){
		this.sock = sock ;
	}
	
	public void run() {
		try {
			while(true){
				InputStream in = sock.getInputStream();
				//����client��������Ϣ
				Message clientMsg = MessageFactory.parseMessage(in);
				Message serverMsg = MessageFactory.convertToServerMessageAndSend(clientMsg,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
