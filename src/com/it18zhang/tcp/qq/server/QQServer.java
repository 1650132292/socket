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
 * Server类
 */
public class QQServer {
	
	private static QQServer server = new QQServer();
	
	public static QQServer getInstance(){
		return server ;
	}
	
	private QQServer(){
	}
	
	//维护所有socket对象
	//key : remoteIP + ":" + remotePort
	private Map<String, Socket> allSockets = new HashMap<String,Socket>();
	
	/**
	 * 启动服务器
	 */
	public void start(){
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true){
				//阻塞
				Socket sock = ss.accept();
				
				InetSocketAddress remoteAddr = (InetSocketAddress)sock.getRemoteSocketAddress();
				//远程ip
				String remoteIp = remoteAddr.getAddress().getHostAddress();
				//远程端口
				int remotePort = remoteAddr.getPort();
				String key= remoteIp + ":" + remotePort;
				allSockets.put(key, sock);
				
				//开起服务器端通信线程
				new CommThread(sock).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到好友列表的串行数据
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
	 * 广播群聊
	 */
	public void broadcast(byte[] bytes) {
		Iterator<Socket> it = allSockets.values().iterator();
		while(it.hasNext()){
			try{
				OutputStream out = it.next().getOutputStream();
				//1.消息类型
				out.write(bytes);
				out.flush();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 发送私聊
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
