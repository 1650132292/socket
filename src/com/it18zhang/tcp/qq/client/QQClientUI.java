package com.it18zhang.tcp.qq.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QQClientUI extends JFrame{

	//��ʷ������
	private JTextArea taHistory ;
	
	//�����б�
	private JList<String> lstFriends  ;
	
	//��Ϣ������
	private JTextArea taInputMessage ;
	
	//���Ͱ�ť
	private JButton btnSend ;
	
	//ˢ�º����б�ť
	private JButton btnRefresh ;
	
	public QQClientUI(){
		init();
		this.setVisible(true);
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100,100, 800, 600);
		this.setLayout(null);
		
		//��ʷ��
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//lstFriends
		lstFriends = new JList<String>();
		lstFriends.setBounds(620, 0, 160, 400);
		this.add(lstFriends);
		
		//taInputMessage
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//btnSend
		btnSend = new JButton("����");
		btnSend.setBounds(560, 420, 100, 160);
		this.add(btnSend);
		
		//btnRefresh
		btnRefresh = new JButton("ˢ��");
		btnRefresh.setBounds(680, 420, 100, 160);
		this.add(btnRefresh);
	}

}