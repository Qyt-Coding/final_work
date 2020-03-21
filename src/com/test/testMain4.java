package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class testMain4 {
	public static void main(String args[]) throws IOException {
		String url="https://m.weibo.cn/statuses/extend?id=4483835964248016";
		String text = null;
		int code = 0;
		while (code != 200 ) {
			try {
				StringBuffer html = new StringBuffer();
				URL Url = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
				// 这个很必要，否则就是403
				conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				code = conn.getResponseCode();
				InputStreamReader isr = new InputStreamReader(conn.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String temp;
				while ((temp = br.readLine()) != null) {
					html.append(temp).append("\n");
				}
				br.close();
				isr.close();
				text = html.toString();
				if(code==302)
				code=200;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code = 0;
			}
			
			System.out.println(text);
		}

	}
}
