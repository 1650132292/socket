package com.it18zhang.udp.screenbroadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Udp���ͷ�
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
	
	//ÿ��frameunit�����ֵ��
	public final static int FRAME_UNIT_MAX = 60 * 1024 ;
	
	public static void main(String[] args) {
		while(true){
			sendOneScreenData();
		}
	}

	/**
	 * ����һ������
	 * 1.����
	 * 2.����
	 * 3.��װUdpPacket(8 + 1 + 1 + 4 + n)
	 * 4.����
	 */
	private static void sendOneScreenData() {
		try {
			//1.����
			byte[] frame = Util.captureScreen();
			
			frame = Util.zipData(frame);
			//2.����
			List<FrameUnit> units = splitFrame(frame);
			
			//3.����untis
			sendUnits(units);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//1.����
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
				
				//����datagramePack
				DatagramPacket pack = new DatagramPacket(bytes, unit.getDateLen() + 14);
				pack.setSocketAddress(new InetSocketAddress("192.168.12.255", 8888));
				sock.send(pack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �и���Ļ���γ�FrameUnit����
	 */
	private static List<FrameUnit> splitFrame(byte[] frame) {
		
		List<FrameUnit> unitList = new ArrayList<FrameUnit>();
		
		//�ж��Ƿ����60K
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
			//���һ��unit
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
