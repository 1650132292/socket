package com.it18zhang.udp.screenbroadcast;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * StudentUI
 */
public class StudentUI extends JFrame{
	
	private JLabel lblIcon ;
	
	public StudentUI(){
		init();
	}

	private void init() {
		this.setTitle("ѧ����");
		this.setBounds(0, 0, 1366, 768);
		this.setLayout(null);
		
		//��ǩ�ռ�
		lblIcon = new JLabel();
		lblIcon.setBounds(0, 0, 1366, 768);
		
		//ͼ��
		ImageIcon icon = new ImageIcon("D:/Koala.jpg");
		lblIcon.setIcon(icon);
		this.add(lblIcon);
		this.setVisible(true);
	}
	
	/**
	 * ����ͼ��
	 */
	public void updateIcon(byte[] dataBytes){
		ImageIcon icon = new ImageIcon(dataBytes);
		lblIcon.setIcon(icon);
	}
}