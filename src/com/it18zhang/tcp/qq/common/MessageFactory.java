package com.it18zhang.tcp.qq.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;

import com.it18zhang.tcp.qq.server.QQServer;

/**
 * ��Ϣ����
 */
public class MessageFactory {
	/**
	 * �����н�����Ϣ ,�����ͻ�����Ϣ����ֱ��ת���ɷ�������Ϣ
	 */
	public static byte[] parseClientMessageAndSend(Socket sock){
		try {
			InputStream in = sock.getInputStream() ;
			
			byte[] msgTypeBytes = new byte[4];
			in.read(msgTypeBytes);
			//��Ϣ
			Message msg = null ;
			
			switch(Util.bytes2Int(msgTypeBytes)){
				//1.Ⱥ��
				case Message.CLIENT_TO_SERVER_CHATS :
					{
						//������Ϣ����
						msg = new ClientChatsMessage();
						//��ȡ��Ϣ����4�ֽ�
						byte[] bytes4 = new byte[4];
						in.read(bytes4);
						int msgLen = Util.bytes2Int(bytes4);
						
						//��ȡ��Ϣ����
						byte[] msgBytes = new byte[msgLen] ;
						in.read(msgBytes);
						((ClientChatsMessage)msg).setMessage(msgBytes);
						
						//ת���ɷ�������Ϣ
						ServerChatsMessage serverMsg = new ServerChatsMessage();
						serverMsg.setSenderInfoBytes(Util.getUserInfo(sock));
						serverMsg.setMsgBytes(((ClientChatsMessage)msg).getMessage());
						
						//ת��������
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						//��Ϣ����(4)
						baos.write(Util.int2Bytes(Message.SERVER_TO_CLIENT_CHATS));
						//userInfoLen(4)
						baos.write(Util.int2Bytes(serverMsg.getSenderInfoBytes().length));
						//userInfo
						baos.write(serverMsg.getSenderInfoBytes());
						//msgLen(4)
						baos.write(Util.int2Bytes(serverMsg.getMsgBytes().length));
						//msg
						baos.write(serverMsg.getMsgBytes());
						
						//�㲥
						QQServer.getInstance().broadcast(baos.toByteArray());
					}
					break ;
				//exit
				case Message.CLIENT_TO_SERVER_EXIT :
					{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						//1.ˢ���б���Ϣ����
						baos.write(Util.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
						//2.�б�����ݳ���
						baos.write(Util.int2Bytes(QQServer.getInstance().getFriendBytes().length));
						//3.�б�����
						baos.write(QQServer.getInstance().getFriendBytes());
						QQServer.getInstance().broadcast(baos.toByteArray());
					}
					break ;
				//ˢ���б�
				case Message.CLIENT_TO_SERVER_REFRESH_FRIENDS :
					{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						//1.ˢ���б���Ϣ����
						baos.write(Util.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
						//2.�б�����ݳ���
						baos.write(Util.int2Bytes(QQServer.getInstance().getFriendBytes().length));
						//3.�б�����
						baos.write(QQServer.getInstance().getFriendBytes());
						QQServer.getInstance().broadcast(baos.toByteArray());
						//���ͺ����б��client
						QQServer.getInstance().send(baos.toByteArray(),Util.getUserInfo(sock));
					}
					break ;
				//˽��
				case Message.CLIENT_TO_SERVER_SINGLE_CHAT :
					{
						//������Ϣ����
						msg = new ClientSingleChatMessage();
						
						byte[] bytes4 = new byte[4];
						//��ȡ�������û���Ϣ����
						in.read(bytes4);
						int recverInfoLen = Util.bytes2Int(bytes4);
						
						//��ȡ�������û���Ϣ
						byte[] recvInfoBytes = new byte[recverInfoLen];
						in.read(recvInfoBytes);
						((ClientSingleChatMessage)msg).setRecverInfoBytes(recvInfoBytes);
						
						//��ȡ��Ϣ����4�ֽ�
						in.read(bytes4);
						int msgLen = Util.bytes2Int(bytes4);
						
						//��ȡ��Ϣ����
						byte[] msgBytes = new byte[msgLen] ;
						in.read(msgBytes);
						((ClientSingleChatMessage)msg).setMessage(msgBytes);
						
						//ת���ɷ�����˽����Ϣ
						ServerSingleChatMessage serverMsg = new ServerSingleChatMessage();
						
						//��������Ϣ
						serverMsg.setSenderInfoBytes(Util.getUserInfo(sock));
						
						//��������Ϣ
						serverMsg.setRecverInfoBytes(((ClientSingleChatMessage)msg).getRecverInfoBytes());
						
						//������Ϣ
						serverMsg.setMessage(((ClientSingleChatMessage)msg).getMessage());
						
						//������Ϣ
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						//1.����
						baos.write(Util.int2Bytes(Message.SERVER_TO_CLIENT_SINGLE_CHAT));
						//2.senderInfoLen
						baos.write(Util.int2Bytes(serverMsg.getSenderInfoBytes().length));
						//3.senderInfo
						baos.write(serverMsg.getSenderInfoBytes());
						//4.msgLen
						baos.write(Util.int2Bytes(serverMsg.getMessage().length));
						baos.write(serverMsg.getMessage());
						
						//����˽�ĸ�������
						QQServer.getInstance().send(baos.toByteArray(),serverMsg.getRecverInfoBytes());
					}
					break ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
}
