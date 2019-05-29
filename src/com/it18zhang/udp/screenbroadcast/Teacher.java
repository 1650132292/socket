package com.it18zhang.udp.screenbroadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Udp发送方
 */
public class Teacher {
	
	private static DatagramSocket sock = null ;
	
	static{
		try {
			sock = new DatagramSocket(9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//每个frameunit的最大值。
	public final static int FRAME_UNIT_MAX = 60 * 1024 ;
	
	public static void main(String[] args) {
		while(true){
			sendOneScreenData();
		}
	}

	/**
	 * 发送一屏数据
	 * 1.截屏
	 * 2.切屏
	 * 3.组装UdpPacket(8 + 1 + 1 + 4 + n)
	 * 4.发送
	 */
	private static void sendOneScreenData() {
		try {
			//1.截屏
			byte[] frame = Util.captureScreen();
			
			frame = Util.zipData(frame);
			//2.切屏
			List<FrameUnit> units = splitFrame(frame);
			
			//3.发送untis
			sendUnits(units);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//1.截屏
	}

	/**
	 * 
	 */
	private static void sendUnits(List<FrameUnit> units) {
		try {
			for(FrameUnit unit : units){
				byte[] bytes = new byte[unit.getDateLen() + 14];
				//frameid
				byte[] frameIdBytes = Util.long2Bytes(unit.getFrameId());
				System.arraycopy(frameIdBytes, 0, bytes, 0, 8);
				
				//unitCount
				bytes[8] = (byte)unit.getUnitCont();
				//unitNo
				bytes[9] = (byte)unit.getUnitNo();
				
				//dataLen
				byte[] dataLenBytes = Util.int2Bytes(unit.getDateLen());
				System.arraycopy(dataLenBytes, 0, bytes, 10, 4);
				
				//data
				System.arraycopy(unit.getData(), 0, bytes, 14, unit.getDateLen());
				
				//构造datagramePack
				DatagramPacket pack = new DatagramPacket(bytes, unit.getDateLen() + 14);
				pack.setSocketAddress(new InetSocketAddress("192.168.12.255", 8888));
				sock.send(pack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切割屏幕，形成FrameUnit集合
	 */
	private static List<FrameUnit> splitFrame(byte[] frame) {
		
		List<FrameUnit> unitList = new ArrayList<FrameUnit>();
		
		//判断是否大于60K
		int count = 0 ;
		if(frame.length % FRAME_UNIT_MAX == 0){
			count = frame.length / FRAME_UNIT_MAX ;
		}
		else{
			count = frame.length / FRAME_UNIT_MAX + 1;
		}
		
		FrameUnit unit = null ;
		long frameId = System.currentTimeMillis();
		for(int i = 0 ; i < count ; i ++){
			unit = new FrameUnit();
			unit.setFrameId(frameId);
			unit.setUnitCont(count);
			unit.setUnitNo(i);
			//最后一个unit
			if(i == (count - 1)){
				if(frame.length % FRAME_UNIT_MAX == 0){
					unit.setDateLen(FRAME_UNIT_MAX);
					byte[] unitData = new byte[FRAME_UNIT_MAX];
					System.arraycopy(frame, i * FRAME_UNIT_MAX, unitData, 0, FRAME_UNIT_MAX);
					unit.setData(unitData);
				}
				else{
					unit.setDateLen(frame.length % FRAME_UNIT_MAX);
					byte[] unitData = new byte[frame.length % FRAME_UNIT_MAX];
					System.arraycopy(frame, i * FRAME_UNIT_MAX, unitData, 0, frame.length % FRAME_UNIT_MAX);
					unit.setData(unitData);
				}
			}
			else{
				unit.setDateLen(FRAME_UNIT_MAX);
				byte[] unitData = new byte[FRAME_UNIT_MAX];
				System.arraycopy(frame, i * FRAME_UNIT_MAX, unitData, 0, FRAME_UNIT_MAX);
				unit.setData(unitData);
			}
			unitList.add(unit);
		}
		//
		return unitList;
	}
}
