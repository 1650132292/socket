package com.it18zhang.udp.screenbroadcast;

/**
 * �и���Ļ��֡��Ԫ
 */
public class FrameUnit {
	// ������id
	private long frameId;

	// ����֮��frameUnit�ĸ���
	private int unitCont;

	// ����֮��ÿ��frameUnit�ı��
	private int unitNo;

	// ���ݳ���
	private int dateLen;

	// ����,frameUnit������
	private byte[] data;

	public long getFrameId() {
		return frameId;
	}

	public void setFrameId(long frameId) {
		this.frameId = frameId;
	}

	public int getUnitCont() {
		return unitCont;
	}

	public void setUnitCont(int unitCont) {
		this.unitCont = unitCont;
	}

	public int getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}

	public int getDateLen() {
		return dateLen;
	}

	public void setDateLen(int dateLen) {
		this.dateLen = dateLen;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
