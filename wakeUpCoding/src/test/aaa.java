package test;

import java.util.Hashtable;

public class aaa {
	public static void main(String[] args) {
		Hashtable<String, Hashtable<String, String>> ht = new Hashtable<>();
		Hashtable<String, String> ht2 = new Hashtable<>();
		ht2.put("1", "11");
		String name = "aa";
		ht.put("테스트1", ht2);
		System.out.println(ht.get("테스트1"));
		ht.get("테스트1").remove("1");
		System.out.println(ht2.get("1"));
	}
}
