package com.util;




public class Utilqyt1 {
	private static class LazyUtilqyt{
		private static final Utilqyt1 util=new Utilqyt1();
	}
	
	public static final Utilqyt1 getInstance() {
		return LazyUtilqyt.util;
	}
	
	
	/**
	 * 这是一个去掉html的方法
	 * @author Administrator
	 */
	public static String removeStrHtml(String str) {
		return str.replaceAll("<[^>]*>", "").trim();
	}
	

}
