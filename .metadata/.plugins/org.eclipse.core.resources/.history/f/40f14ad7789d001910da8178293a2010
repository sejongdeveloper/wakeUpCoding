package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
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
	
	public ClientUI() {
		panWest = new JPanel(new GridLayout(3, 0,10,10));
		panWest.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p1chat = new JPanel(new BorderLayout(10,10));
		p1chat.add(new JLabel("접속자"), BorderLayout.NORTH);
			uList = new Vector<String>(); uList.add("a");uList.add("a");uList.add("a");uList.add("a");uList.add("a");
			userList = new JList<String>(uList);
			userScroll = new JScrollPane(userList);
			userScroll.setSize(70, 50);
		p1chat.add(userScroll, BorderLayout.CENTER);
		panWest.add(p1chat);
		
		p2chatList = new JPanel(new BorderLayout(10,10));
		p2chatList.add(new JLabel("채팅방"), BorderLayout.NORTH);
			rList = new Vector<String>(); rList.add("newChat");rList.add("newChat");rList.add("newChat");rList.add("newChat");rList.add("newChat");
			roomList = new JList<String>(rList);
			roomScroll = new JScrollPane(roomList);
			roomScroll.setSize(70, 50);
		p2chatList.add(roomScroll, BorderLayout.CENTER);
		panWest.add(p2chatList);
		
		p3btns = new JPanel(new GridLayout(2,0,10,10));
		p3btns.add(btnJoin = new JButton("채팅방 참여"));
		p3btns.add(btnNewRoom = new JButton("새 채팅방"));
		btnJoin.setSize(50, 15); btnNewRoom.setSize(50, 15);
		panWest.add(p3btns);
		
		panEast = new JPanel(new BorderLayout(10, 10));
		panEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		chatArea = new TextArea("채팅 내용\n", 1, 1, TextArea.SCROLLBARS_VERTICAL_ONLY);
		chatArea.setBounds(10, 10, 10, 10);
		panEast.add(chatArea, BorderLayout.CENTER);
		
		chatField = new JTextField(28);
		p4writeChat = new JPanel(new FlowLayout());
		p4writeChat.add(chatField);
		p4writeChat.add(btnEnter = new JButton("전송"));
		panEast.add(p4writeChat, BorderLayout.SOUTH);

		this.add(panWest, "West");
		this.add(panEast, "East");
		setBounds(100, 100, 550, 400);
		setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
