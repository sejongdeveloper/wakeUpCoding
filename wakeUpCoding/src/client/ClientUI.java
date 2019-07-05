package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ClientUI extends JFrame{
	JPanel panWest, panEast;
	JPanel p1chat, p2chatList, p3btns, p4writeChat;
	JScrollPane userScroll, roomScroll;
	JButton btnJoin, btnNewRoom, btnEnter;
	TextArea chatArea;
	JTextField chatField;
	JList<String> userList, roomList; 
	Vector<String> uList, rList; ///////////
	
	public static void main(String[] args) {
		new ClientUI();
	}
	
	public ClientUI() {
		setLayout(null);
		panWest = new JPanel(new GridLayout(3, 0,10,10));
		panWest.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p1chat = new JPanel(new BorderLayout(10,10));
		p1chat.add(new JLabel("접속자"), BorderLayout.NORTH);
			uList = new Vector<String>(); 
			userList = new JList<String>(uList);
			userScroll = new JScrollPane(userList);
			userScroll.setSize(5, 5);
		p1chat.add(userScroll, BorderLayout.CENTER);
		panWest.add(p1chat);
		
		p2chatList = new JPanel(new BorderLayout(10,10));
		p2chatList.add(new JLabel("채팅방"), BorderLayout.NORTH);
			rList = new Vector<String>(); 
			roomList = new JList<String>(rList);
			roomScroll = new JScrollPane(roomList);
			roomScroll.setSize(5, 5);
		p2chatList.add(roomScroll, BorderLayout.CENTER);
		panWest.add(p2chatList);
		
		p3btns = new JPanel(new GridLayout(2,0,10,10));
		p3btns.add(btnJoin = new JButton("채팅방 참여"));
		p3btns.add(btnNewRoom = new JButton("새 채팅방"));
		btnJoin.setSize(5, 5); btnNewRoom.setSize(5, 5);
		panWest.add(p3btns);
		
		panWest.setMinimumSize(getSize());
		panEast = new JPanel(new BorderLayout(10, 10));
		panEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		chatArea = new TextArea("채팅 내용\n", 1, 1, TextArea.SCROLLBARS_VERTICAL_ONLY);
		panEast.add(chatArea, BorderLayout.CENTER);
		
		chatField = new JTextField(28);
		p4writeChat = new JPanel(new FlowLayout());
		p4writeChat.add(chatField);
		p4writeChat.add(btnEnter = new JButton("전송"));
		panEast.add(p4writeChat, BorderLayout.SOUTH);
		
		panWest.setBounds(3, 3, 130, 350);
		panEast.setBounds(130, 3, 410, 350);
		
		this.add(panWest);
		this.add(panEast);
		setBounds(100, 150, 560, 400);
		setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
