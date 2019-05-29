package com.it18zhang.udp.screenbroadcast;

/**
 * 切割屏幕的帧单元
 */
public class FrameUnit {
	// 截屏的id
	private long frameId;

	// 切屏之后，frameUnit的个数
	private int unitCont;

	// 切屏之后，每个frameUnit的编号
	private int unitNo;

	// 数据长度
	private int dateLen;

	// 数据,frameUnit的内容
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
