package com.test;

public class testMain3 {
	
	public static void main(String args[]) {
		String str="#央视采访奔驰维权车主#跪求广大网友顶我上去，让更多人看见我们家的冤情。兴城水爷官压百姓，贪污受贿铁证如山，多人举报无人给其定罪，官官相护篡改卷宗，至今逍遥法外。被逼倾家荡产借钱替王亚斌垫付全部工资"
				+ "，却被无故羁押九个月，多次取保被拒，法官跟我说只要我把对我父亲的无罪辩护和证人证言撤 ...全文";
		System.out.println(str.indexOf("...全文"));
		
		
		String str1="\"1231456\"";
		System.out.println(str1.replaceAll("\"", ""));
	} 
	
}
