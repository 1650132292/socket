package com.it18zhang.tcp.qq.common;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Util {
	
	/**
	 * ��long������ת����byte[] 
	 */
	public static byte[] long2Bytes(long l){
		byte[] bytes = new byte[8] ;
		bytes[0] = (byte)l ;
		bytes[1] = (byte)(l >> 8) ;
		bytes[2] = (byte)(l >> 16) ;
		bytes[3] = (byte)(l >> 24) ;
		bytes[4] = (byte)(l >> 32) ;
		bytes[5] = (byte)(l >> 40) ;
		bytes[6] = (byte)(l >> 48) ;
		bytes[7] = (byte)(l >> 56) ;
		return bytes ;
	}
	
	public static long byte2Long(byte[] bytes){
		return ((long)(bytes[0] & 0xFF))
				| ((long)(bytes[1] & 0xFF) << 8)
				| ((long)(bytes[2] & 0xFF) << 16)
				| ((long)(bytes[3] & 0xFF) << 24)
				| ((long)(bytes[4] & 0xFF) << 32)
				| ((long)(bytes[5] & 0xFF) << 40)
				| ((long)(bytes[6] & 0xFF) << 48)
				| ((long)(bytes[7] & 0xFF) << 56);
	}
	
	/**
	 * ��long������ת����byte[] 
	 */
	public static byte[] int2Bytes(int l){
		byte[] bytes = new byte[8] ;
		bytes[0] = (byte)l ;
		bytes[1] = (byte)(l >> 8) ;
		bytes[2] = (byte)(l >> 16) ;
		bytes[3] = (byte)(l >> 24) ;
		return bytes ;
	}
	
	public static int bytes2Int(byte[] bytes){
		return ((bytes[0] & 0xFF))
				| ((bytes[1] & 0xFF) << 8)
				| ((bytes[2] & 0xFF) << 16)
				| ((bytes[3] & 0xFF) << 24);
	}
	
	public static int byte2Int(byte[] bytes,int offset){
		return ((bytes[0 + offset] & 0xFF))
				| ((bytes[1 + offset] & 0xFF) << 8)
				| ((bytes[2 + offset] & 0xFF) << 16)
				| ((bytes[3 + offset] & 0xFF) << 24);
	}

	/**
	 * ���л�����
	 */
	public static byte[] serializeObject(Serializable src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.close();
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ͨ��sock�õ�Զ�̿ͻ���user��Ϣ
	 */
	public static byte[] getUserInfo(Socket sock){
		try {
			InetSocketAddress addr = (InetSocketAddress)sock.getRemoteSocketAddress();
			String ip = addr.getAddress().getHostAddress();
			int port = addr.getPort();
			return (ip + ":" + port).getBytes() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
}