package chatting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClientChat extends JFrame implements ActionListener {
	// login gui ����
	private JFrame log = new JFrame();
	private JPanel logPane1;
	private JTextField ipserver;
	private JTextField portnum;
	private JTextField userid;
	JButton logbtn = new JButton("����");
	// Ŭ���̾�Ʈ
	private JPanel contentPane;
	private JTextField msgtf;
	private JButton sendnote = new JButton("����������");
	private JLabel lblNewLabel_1 = new JLabel("ä�ù���");
	private JButton joinroom = new JButton("ä�ù�����");
	private JButton createroom = new JButton("�游���");
	private JButton send = new JButton("����");
	private JList userlist = new JList(); // ������ ����Ʈ
	private JList roomlist = new JList(); // ���� ����Ʈ
	private JTextArea textArea = new JTextArea();

	private Socket socket;
	String ip = "";
	int port;
	String id;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	// �׿� ����
	Vector useral = new Vector();
	Vector roomvc = new Vector();
	StringTokenizer st;
	private String My_Room;

	ClientChat() {
		Login_init();
		Main_init();
		start();
	}

	public void start() {
		logbtn.addActionListener(this);
		sendnote.addActionListener(this);
		joinroom.addActionListener(this);
		createroom.addActionListener(this);
		send.addActionListener(this);
	}

	public void Main_init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("��ü������");
		lblNewLabel.setBounds(33, 28, 57, 15);
		contentPane.add(lblNewLabel);

		userlist.setBounds(12, 59, 87, 98);
		contentPane.add(userlist);

		sendnote.setBounds(12, 188, 97, 23);
		contentPane.add(sendnote);

		lblNewLabel_1.setBounds(12, 238, 97, 15);
		contentPane.add(lblNewLabel_1);

		roomlist.setBounds(12, 278, 97, 65);
		contentPane.add(roomlist);

		joinroom.setBounds(12, 353, 97, 23);
		contentPane.add(joinroom);

		createroom.setBounds(12, 386, 97, 23);
		contentPane.add(createroom);

		textArea.setBounds(111, 36, 540, 373);
		contentPane.add(textArea);

		msgtf = new JTextField();
		msgtf.setBounds(92, 419, 473, 50);
		contentPane.add(msgtf);
		msgtf.setColumns(10);

		send.setBounds(577, 424, 113, 38);
		contentPane.add(send);

		this.setVisible(true);
	}

	public void Login_init() {
		log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		log.setBounds(100, 100, 279, 497);
		logPane1 = new JPanel();
		logPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		log.setContentPane(logPane1);
		logPane1.setLayout(null);

		JLabel lblNewLabel = new JLabel("���� ip");
		lblNewLabel.setBounds(27, 248, 57, 15);
		logPane1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("��Ʈ��ȣ");
		lblNewLabel_1.setBounds(27, 295, 57, 15);
		logPane1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("����ھ��̵�");
		lblNewLabel_2.setBounds(27, 347, 85, 15);
		logPane1.add(lblNewLabel_2);

		ipserver = new JTextField();
		ipserver.setBounds(96, 248, 116, 21);
		logPane1.add(ipserver);
		ipserver.setColumns(10);
		ipserver.setText("localhost"); // IP �����߰� (����)
		
		portnum = new JTextField();
		portnum.setBounds(96, 292, 116, 21);
		logPane1.add(portnum);
		portnum.setColumns(10);
		portnum.setText("5000"); // ��Ʈ��ȣ �����߰� (����)

		userid = new JTextField();
		userid.setBounds(111, 344, 116, 21);
		logPane1.add(userid);
		userid.setColumns(10);

		logbtn.setBounds(12, 408, 167, 23);
		logPane1.add(logbtn);

		log.setVisible(true);
	}

	private void Network() {

		try {
			socket = new Socket(ip, port);
			if (socket != null) {
				Connect();

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void Connect() {
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// ó�����ӽ� id����
		Sendmsg(id);

		useral.add(id);
		userlist.setListData(useral);

		Thread th = new Thread(new Runnable() { // inner class

			@Override
			public void run() {
				while (true) {
					try {
						String msg = dis.readUTF();

						System.out.println("���� �޼���  : " + msg);

						inmsg(msg);
					} catch (IOException e) {
						e.printStackTrace();
					} // �޼��� ����

				}

			}
		});
		th.start();
	}

	private void inmsg(String str) { // �����޼��� �ٵ���
		st = new StringTokenizer(str, "/");

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("��������  : " + protocol);
		System.out.println("����  : " + Message);
		// ��ū�� ���ؼ� �ش繮�ڿ��� ���н�����

		if (protocol.equals("NewUser")) {
			useral.add(Message);
			userlist.setListData(useral);

		} else if (protocol.equals("OldUser")) {
			useral.add(Message);
			userlist.setListData(useral);
		} else if (protocol.equals("Note")) {
			st = new StringTokenizer(Message, "@");
			String user = st.nextToken();
			String note = st.nextToken();

			System.out.println(user + "�κ��� �� ����" + note);
			JOptionPane.showMessageDialog(null, note, user + "�����κ��� ������", JOptionPane.CLOSED_OPTION);
		} else if (protocol.equals("CreateRoom")) {// �������������
			My_Room = Message;

		} else if (protocol.equals("New_room")) {
			roomvc.add(Message);
			roomlist.setListData(roomvc);
		} else if (protocol.equals("CreateRoomfail")) {// ��������������
			JOptionPane.showMessageDialog(null, "�游��� ����", "�˸�", JOptionPane.ERROR_MESSAGE);

			roomvc.add(Message);
			roomlist.setListData(roomvc);
		} else if (protocol.equals("Chatting")) {
			String msg = st.nextToken();
			textArea.append(Message + " : " + msg + "\n");
		} else if (protocol.equals("OldRoom")) {
			roomvc.add(Message);

		} else if (protocol.equals("roomvc_update")) {
			roomlist.setListData(roomvc);

		} else if (protocol.equals("joinRoom")) {
			My_Room = Message;
			JOptionPane.showMessageDialog(null, "������ ����", "�˸�", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void Sendmsg(String str) {// �������� �޼����� ������ �κ�
		// outputstream�� ���� ����
		try {
			dos.writeUTF(str); // ���ڿ��� �޾Ƽ� dos.writeUTF�� ����
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new ClientChat();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logbtn) {
			// loginbtn
			System.out.println("�α��ι�ư Ŭ��");
			ip = ipserver.getText().trim(); // ip�޾ƿ�
			port = Integer.parseInt(portnum.getText().trim()); // port�޾ƿ�
			id = userid.getText().trim(); // id�޾ƿ�
			// port �� int���¶� String�� getText�� �޾� �ü� ���� �׷��� ����ȯ�� �ؾ���.
			Network();
			log.setVisible(false);

		} else if (e.getSource() == sendnote) {
			System.out.println("���������� Ŭ��");
			String user = (String) userlist.getSelectedValue();

			String note = JOptionPane.showInputDialog("�����޼���");
			if (note != null) {
				Sendmsg("Note/" + user + "@" + note);
				System.out.println("�޴»��  : " + user + "��������" + note);

			}

		} else if (e.getSource() == joinroom) {
			String JoinRoom = (String) roomlist.getSelectedValue();
			Sendmsg("joinRoom/" + JoinRoom);
			System.out.println("������");
		} else if (e.getSource() == createroom) {
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			if (roomname != null) { // ���̸��� �Էµ��ִ� ���
				Sendmsg("CreateRoom/ " + roomname);// �޼����� ���Ͽ� ���̸��� �����ش�.
			}
			System.out.println("�游���");
		} else if (e.getSource() == send) {
			Sendmsg("Chatting/" + My_Room + "/" + msgtf.getText().trim());
			// ���� ä�ù� + �޼��� + ���̸�
			System.out.println("������");
		}
		//aa
	}
}
