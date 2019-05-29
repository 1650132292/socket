package com.it18zhang.tcp.qq.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QQClientUI extends JFrame{

	//历史聊天区
	private JTextArea taHistory ;
	
	//好友列表
	private JList<String> lstFriends  ;
	
	//消息输入区
	private JTextArea taInputMessage ;
	
	//发送按钮
	private JButton btnSend ;
	
	//刷新好友列表按钮
	private JButton btnRefresh ;
	
	public QQClientUI(){
		init();
		this.setVisible(true);
	}

	/**
	 * 初始化布局
	 */
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100,100, 800, 600);
		this.setLayout(null);
		
		//历史区
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
		btnSend = new JButton("发送");
		btnSend.setBounds(560, 420, 100, 160);
		this.add(btnSend);
		
		//btnRefresh
		btnRefresh = new JButton("刷新");
		btnRefresh.setBounds(680, 420, 100, 160);
		this.add(btnRefresh);
	}

}