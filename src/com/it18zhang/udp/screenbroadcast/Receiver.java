package com.it18zhang.udp.screenbroadcast;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class Receiver extends Thread {
	private DatagramSocket sock ; 
	private DatagramPacket pack ;
	private byte[] buf = new byte[Teacher.FRAME_UNIT_MAX + 14];
	
	//
	private Map<Integer, FrameUnit> unitMap = new HashMap<Integer,FrameUnit>();
	
	//
	private StudentUI ui;
	
	public Receiver(StudentUI ui){
		try {
			this.ui = ui ;
			sock = new DatagramSocket(8888);
			pack = new DatagramPacket(buf, buf.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			long currentFrameId = 0 ;
			while(true){
				sock.receive(pack);
				int len = pack.getLength();
				//转换成帧单元
				FrameUnit funit = convertData2FrameUnit(buf,0,len);
				long newFrameId = funit.getFrameId();
				//同一frame
				if(newFrameId == currentFrameId){
					unitMap.put(funit.getUnitNo(), funit);
				}
				//新frame
				else if(newFrameId > currentFrameId){
					currentFrameId = newFrameId ;
					unitMap.clear();
					unitMap.put(funit.getUnitNo(), funit);
				}
				processFrame();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理整个unit集合
	 */
	private void processFrame() {
		try {
			//得到Frame中总的unit个数
			int unitCont = unitMap.values().iterator().next().getUnitCont();
			if(unitMap.size() == unitCont ){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				for(int i = 0 ; i < unitCont ; i ++){
					FrameUnit fu = unitMap.get(i);
					fu.getData();
					baos.write(fu.getData());
				}
				unitMap.clear();
				
				//
				ui.updateIcon(Util.unzipData(baos.toByteArray()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转换每个pack为Frameunit
	 */
	private FrameUnit convertData2FrameUnit(byte[] buf2, int i, int len) {
		byte[] data = new byte[len];
		System.arraycopy(buf2, 0, data, 0, len);
		
		FrameUnit unit = new FrameUnit();
		unit.setFrameId(Util.byte2Long(data));
		unit.setUnitCont(data[8]);
		unit.setUnitNo(data[9]);
		unit.setDateLen(Util.byte2Int(data,10));
		
		byte[] frameUnitBytes = new byte[unit.getDateLen()];
		System.arraycopy(buf2, 14, frameUnitBytes, 0, unit.getDateLen());
		
		unit.setData(frameUnitBytes);
		return unit;
	}
}
