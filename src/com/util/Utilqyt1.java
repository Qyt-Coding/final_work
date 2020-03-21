package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class Utilqyt1 {
	private static class LazyUtilqyt {
		private static final Utilqyt1 util = new Utilqyt1();
	}

	public static final Utilqyt1 getInstance() {
		return LazyUtilqyt.util;
	}

	/**
	 * 这是一个去掉html的方法
	 * 
	 * @author Administrator
	 */
	public String removeStrHtml(String str) {
		return str.replaceAll("<[^>]*>", "").trim();
	}

	/**
	 * 解决全文的重定向问题
	 */
	public String resolveRedir(String url) {
		// String url="https://m.weibo.cn/statuses/extend?id=4483835964248016";
		String text = null;
		int code = 0;
		while (code != 200) {
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
				if (code == 302)
					code = 200;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code = 0;
			}

			System.out.println(text);
		}
		return text;
	}

	/**
	 * 使用HttpClient
	 */
	@Test
	public String  resolveRedir1(String url) {
		String text="";
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet client = new HttpGet(url);
		client.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		CloseableHttpResponse response = null;

		try {
			response = httpClient.execute(client);
		 HttpEntity entity=	response.getEntity();
		 	
			if(response!=null) {
				System.out.println(response);
				text=EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		  return text;
	}

}
