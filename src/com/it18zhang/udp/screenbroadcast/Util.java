package com.it18zhang.udp.screenbroadcast;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

public class Util {
	//
	private static Robot robot ;
	
	//
	static{
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ץͼ
	 */
	public static byte[] captureScreen(){
		try {
			BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, 1366, 768));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
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
	 * ���ص�ѹ���������ֽ�����
	 */
	public static byte[] zipData(byte[] rawData) {
		try {
			//
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			zos.putNextEntry(new ZipEntry("one"));
			zos.write(rawData);
			zos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���ص�ѹ���������ֽ�����
	 */
	public static byte[] unzipData(byte[] zipData) {
		try {
			//
			ByteArrayInputStream bais = new ByteArrayInputStream(zipData);
			ZipInputStream zis = new ZipInputStream(bais);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			zis.getNextEntry();
			byte[] buf = new byte[1024];
			int len = 0 ;
			while((len = zis.read(buf)) != -1){
				baos.write(buf, 0, len);
			}
			zis.close();
			bais.close();
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
