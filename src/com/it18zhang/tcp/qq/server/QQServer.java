package com.it18zhang.tcp.qq.server;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.it18zhang.tcp.qq.common.Message;
import com.it18zhang.tcp.qq.common.ServerChatsMessage;
import com.it18zhang.tcp.qq.common.ServerRefreshFriendsMessage;
import com.it18zhang.tcp.qq.common.ServerSingleChatMessage;
import com.it18zhang.tcp.qq.common.Util;

/**
 * Server��
 */
public class QQServer {
	
	private static QQServer server = new QQServer();
	
	public static QQServer getInstance(){
		return server ;
	}
	
	private QQServer(){
	}
	
	//ά������socket����
	//key : remoteIP + ":" + remotePort
	private Map<String, Socket> allSockets = new HashMap<String,Socket>();
	
	/**
	 * ����������
	 */
	public void start(){
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true){
				//����
				Socket sock = ss.accept();
				
				InetSocketAddress remoteAddr = (InetSocketAddress)sock.getRemoteSocketAddress();
				//Զ��ip
				String remoteIp = remoteAddr.getAddress().getHostAddress();
				//Զ�̶˿�
				int remotePort = remoteAddr.getPort();
				String key= remoteIp + ":" + remotePort;
				allSockets.put(key, sock);
				
				//�����������ͨ���߳�
				new CommThread(sock).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �õ������б�Ĵ�������
	 * @return
	 */
	public byte[] getFriendBytes(){
		try {
			List<String> list = new ArrayList<String>(allSockets.keySet());
			return Util.serializeObject((Serializable)list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}


	/**
	 * �㲥Ⱥ��
	 */
	public void broadcast(byte[] bytes) {
		Iterator<Socket> it = allSockets.values().iterator();
		while(it.hasNext()){
			try{
				OutputStream out = it.next().getOutputStream();
				//1.��Ϣ����
				out.write(bytes);
				out.flush();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ����˽��
	 */
	public void send(byte[] msg , byte[] userInfo) {
		try{
			String key = new String(userInfo);
			OutputStream out = allSockets.get(key).getOutputStream();
			out.write(msg);
			out.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
