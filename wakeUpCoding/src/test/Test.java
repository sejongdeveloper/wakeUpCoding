package test;

import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		
		Hashtable<String, Hashtable<String,Socket>> roomHash = new Hashtable<String, Hashtable<String,Socket>>();
		
		Socket s = new Socket();
		roomHash.put("proto", new Hashtable<String, Socket>());
		roomHash.get("proto").put("����", s);
		

		roomHash.put("new1", new Hashtable<String, Socket>());
		roomHash.get("new1").put("����", s);
		roomHash.get("new1").put("aa", s);
		roomHash.get("new1").put("bb", s);
		
		Set<String> str = roomHash.keySet();
		for(String strstr : str) {
			System.out.println(strstr);
		}
		
		System.out.println("\n=========================\n");
		
		Set<String> str2 = roomHash.get("new1").keySet();
		for(String strstr : str2) {
			System.out.println(strstr);
			System.out.println(roomHash.get("new1").get(strstr));
		}
	}
}
