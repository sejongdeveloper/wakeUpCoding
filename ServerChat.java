package chatting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import chatting.ServerChat.UserInfo;

public class ServerChat extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField porttextfield;
	private JTextArea textArea = new JTextArea();
	private JButton startbtn = new JButton("서버실행");
	private JButton exitbtn = new JButton("서버종료");
	// net work
	private ServerSocket ss;
	private Socket s;
	private int port;  
	private Vector uservc = new Vector();
	private StringTokenizer st;
	private Vector roomvc = new Vector();

	ServerChat() { // 생성자
		init();// 화면 생성 메소드
		start();

	}

	private void start() {
		startbtn.addActionListener(this);
		exitbtn.addActionListener(this);

	}

	public void Server_start() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ss != null) {
			connect();

		}

	}

	private void connect() {

		Thread th = new Thread(new Runnable() {// inner class

			@Override
			public void run() { // 스레드 구동부
				while (true) {

					try {
						textArea.append("대기중\n");
						s = ss.accept();
						textArea.append("접속완료");

						UserInfo user = new UserInfo(s);

						user.start();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while end

			}
		});
		th.start();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 30, 314, 172);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("포트번호");
		lblNewLabel.setBounds(12, 202, 71, 49);
		contentPane.add(lblNewLabel);

		porttextfield = new JTextField();
		porttextfield.setBounds(98, 216, 174, 35);
		contentPane.add(porttextfield);
		porttextfield.setColumns(10);
		porttextfield.setText("5000"); //포트번호 고정추가 (세종)

		startbtn.setBounds(284, 215, 97, 23);
		contentPane.add(startbtn);

		exitbtn.setBounds(284, 238, 97, 23);
		contentPane.add(exitbtn);

		this.setVisible(true);// 트루일경우 화면에나옴

	}

	public static void main(String[] args) {
		new ServerChat();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startbtn) {
			System.out.println("스타트 버튼 클릭");
			port = Integer.parseInt(porttextfield.getText().trim());
			Server_start();// 버튼누르면 서버 시작

		} else if (e.getSource() == exitbtn) {
			System.out.println("종료 버튼 클릭");
		}
	}// 이벤트

	class UserInfo extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private Socket user_socket;
		private String Nickname = "";

		private boolean Roomcheck = true;

		public UserInfo(Socket soc) { // 생성자 함수
			this.user_socket = soc;

			UserNetwork();
		}

		public void UserNetwork() {
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);

				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				Nickname = dis.readUTF();
				textArea.append(Nickname + " 사용자 접속 ");

				// 새로들어온 사용자 추가
				Broad("NewUser/" + Nickname);
				// 자신에게 받아오기 .
				for (int i = 0; i < uservc.size(); i++) {
					UserInfo u = (UserInfo) uservc.elementAt(i);
					sendM("OldUser/"+u.Nickname);
				}

				//자신에게 기존 방 목록을 받아온다.
				for (int i = 0; i < roomvc.size(); i++) {
					Roominfo r = (Roominfo) roomvc.elementAt(i);
					sendM("OldRoom/"+r.Room_name);
				}
				
				for(int i = 0; i< roomvc.size(); i++) {
					Roominfo r = (Roominfo) roomvc.elementAt(i);
					sendM("roomvc_update/" + r.Room_name);
					
				}
				
				
				uservc.add(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void Broad(String str) {
			for (int i = 0; i < uservc.size(); i++) {// 전체 사용자에게 메세지
				UserInfo u = (UserInfo) uservc.elementAt(i);
				u.sendM(str);

				// 사용자들이 들어있으면 백터에서 사용자를 하나 꺼내 userinfo형으로 변환하여 한명을 꺼냄
			}

		}

		public void run() { // thread 에서 처리
			while (true) {
				try {
					String msg = dis.readUTF(); // 메세지수신
					textArea.append(Nickname + "으로 부터 들어온 메세지" + msg);
					Inmsg(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} // 메세지 수신

			} // while end
		}

		public void Inmsg(String str) {
			st = new StringTokenizer(str, "/");
			
			String protocol =st.nextToken();
			String message = st.nextToken();
			System.out.println("프로토콜   : " + protocol);
			System.out.println("메세지   : " + message);
			
			
			if(protocol.equals("Note"))
			{
				st = new StringTokenizer(message, "@");
				
				String user = st.nextToken();
				String note = st.nextToken();
				System.out.println("받는 사람  : " + user);
				System.out.println("보낼 내용  : "  +  note);
				for(int i = 0; i <uservc.size(); i ++) {
					UserInfo u = (UserInfo)uservc.elementAt(i);
					if(u.Nickname.equals(user)) {
						u.sendM("Note" + Nickname+"@"+note);
					}
				}
			
			}
			else if(protocol.equals("CreateRoom")) {
				//1.같은이름의 방이있는지 찾아보기 
				for(int i =0; i<roomvc.size(); i++) {
					Roominfo r = (Roominfo)roomvc.elementAt(i);
					if(r.Room_name.equals(message)){ //만들고자하는 방의 이름이 동일한 방이 있을경우 
						sendM("CreateRoomfail/ok");
						Roomcheck = false; //방을만들수없다. 
						break;
					}
					
				}//for end 
				if(Roomcheck)//방을 만들 수 있을때 
				{
					Roominfo new_room  = new Roominfo(message,this);
					roomvc.add(new_room);//저체 백터에 만들어진 새방을 추 가 
					sendM("CreateRoom/"+message);
					
					Broad("New_room/"+ message);
				}
				Roomcheck = true;
			}else if(protocol.equals("Chatting")) {
				String msg = st.nextToken();//방이름 
				for(int i =0; i<roomvc.size(); i++) {
					Roominfo r = (Roominfo)roomvc.elementAt(i);
							if(r.Room_name.equals(message)) { //해당방을 찾아씅면 
								r.BroadRoom("Chatting/" + Nickname + "/" +msg);
				}
					}
				}else if(protocol.equals("joinRoom")) {
					for(int i =0 ; i < roomvc.size(); i++) {
						Roominfo r = (Roominfo)roomvc.elementAt(i);
						if(r.Room_name.equals(message)) {
							//사용자추가 
							r.Add_User(this);
							sendM("joinRoom/"+message);
						}
					}
				}
		}

		public void sendM(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}// end userinfo

class Roominfo {
	String Room_name;
	Vector Room_uservc = new Vector();

	Roominfo(String str, UserInfo u) {
		this.Room_name = str;
		this.Room_uservc.add(u);

	}
	public void BroadRoom(String str){
		//현재방에 모든사람에게 채팅 
		for(int i = 0; i<Room_uservc.size(); i++) {
			UserInfo u = (UserInfo)Room_uservc.elementAt(i);
			u.sendM(str);
			
		}
		
	}
	
	public void Add_User(UserInfo u) {
		this.Room_uservc.add(u);
	}
	
}