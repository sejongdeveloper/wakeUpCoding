package TestStart;

import client.login.Login;
import server.Server;

public class Start {
	public static void main(String[] args) {
		System.out.println("서버시작~!");
		new Server();
		new Login();
		new Login();
		new Login();
	}
}
