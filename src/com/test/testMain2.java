package com.test;

public class testMain2 {
/**
 * 使用正则去掉
 * @param args
 */
	
	public static void main(String args[]) {
		String str="我关注了电影解说超话，小伙伴们快来加入电影解说超话一起聊聊吧~戳这里→ <a data-url=\"htt"
				+ "p://t.cn/RXTsLLU\" href=\"https://m.weibo.cn/p/index?containerid=100808fde9813f3def"
				+ "ce687049888a05b97172&extparam=%E7%94%B5%E5%BD%B1%E8%A7%A3%E8%AF%B4&luicode=10000011&lfi"
				+ "d=1076036486005242\" data-hide=\"\"><span class='url-icon'><img style='width: 1rem;height: 1rem' src='http://n.sinaimg.cn/photo/5213b46e/20181127/"
				+ "timeline_card_small_super_default.png'></span><span class=\"surl-text\">电影解说超话</span></ a> ";
		System.out.println(str);
		System.out.println("----------------------------------------");
			System.out.println(str.replaceAll("<[^>]*>", "").trim());
			
			 String html1 = "<p>测试1</p>".replaceAll("<[^>]*>", "").trim();
			String html2 = "<p>测试2</p>".replaceAll("<[^>]*>", "").trim();
			String html3 = "<p>测试3</p>".replaceAll("<[^>]*>", "").trim();
			String html4 = "<p>测试4</p>".replaceAll("<[^>]*>", "").trim();
			System.out.println(html1);
			System.out.println(html2);
			System.out.println(html3);
			System.out.println(html4);
	}
}
