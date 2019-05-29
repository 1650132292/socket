package com.it18zhang.udp.screenbroadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * 屏广发送器
 */
public class ScreenBroadCastSender {
	private InetSocketAddress bcAddr ;
	private DatagramSocket socket ;
	
	public ScreenBroadCastSender(){
		try {
			socket = new DatagramSocket(8888);
			bcAddr = new InetSocketAddress("192.168.12.255", 9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送unit列表
	 */
	public void send(List<FrameUnit> unitList){
		try {
			//发送每个FrameUnit
			for(FrameUnit unit : unitList){
				byte[] packetData = popFrameUnit(unit);
				DatagramPacket pack = new DatagramPacket(packetData, packetData.length);
				pack.setSocketAddress(bcAddr);
				socket.send(pack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 组装帧单元数据到byte[]
	 */
	private byte[] popFrameUnit(FrameUnit unit){
		//
		byte[] bytes = new byte[unit.getDateLen() + 14] ;
		
		//设置frameId
		byte[] frameIdBytes = Util.long2Bytes(unit.getFrameId());
		System.arraycopy(frameIdBytes, 0, bytes, 0, 8);
		
		//frameUnitCount
		bytes[8] = (byte)unit.getUnitCont();
		//frameUnitNo
		bytes[9] = (byte)unit.getUnitNo();
		
		//dataLen
		byte[] dateLenBytes = Util.int2Bytes(unit.getDateLen());
		System.arraycopy(dateLenBytes, 0, bytes, 10, 4);
		
		//数据
		System.arraycopy(unit.getData(), 0, bytes, 14, unit.getDateLen());
		
		return bytes ;
	}
}
