package com.second.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DataMining.Doc;
import DataMining.PostingItem;

public class Utilsqyt {
	
	private static class InnerQyt{
		private InnerQyt() {
			
		}
		private static Utilsqyt utils=new Utilsqyt();
		
		private static Utilsqyt getInstance() {
			return utils;
		}
	}
	/**
	 * 懒汉模式
	 * @return
	 */
    public static Utilsqyt getInstance() {
    	return InnerQyt.getInstance();
    }	
	

	public static  int weidu;
	public static  int docNum;
	public static  int _k;

	public static double MAX = 10000000;
	
	//------------------------------------------------------
	private int docCount;// 文档的数量
	private int wordCount;// 单词的数量
	private Connection conn;
	
	public boolean connectDB() {
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/zplweibo?useUnicode=true&characterEncoding=utf8";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "123456";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 关闭连接
	 */
	
	public boolean closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 导出doc的数值
	 * 
	 * @return
	 */
	public List<Doc> getDocList() {
		List<Doc> docList = new ArrayList<Doc>();
		String cmdString = "select * from doc";
		Statement ste;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while (resultSet.next()) {
				int docID = resultSet.getInt("docID");
				String DocText = resultSet.getString("DocText");
				Doc doc = new Doc();
				doc.setDocID(docID);
				doc.setDocCon(DocText);
				docList.add(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docList;
	}
	/**
	 * 返回lex的行数
	 * @return
	 */
	
	public int getLexNum() {
		String cmdString = "select count(*) from lexcion";
		Statement ste;
		int lexNum = 0;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while (resultSet.next()) {
				lexNum = resultSet.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lexNum;
	}
	
	
	/**
	 * 根据docID在lex表中获得对应的值
	 */
	public List<PostingItem> getPostingItemByDocId(int docId) {
		List<PostingItem> postingList = new ArrayList();
		String cmdString = "SELECT * from postingitem where docID='" + docId + "'";
		Statement ste;
		int lexNum = 0;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while (resultSet.next()) {
				PostingItem posting = new PostingItem();
				posting.setId(resultSet.getInt("id"));
				posting.setLexiconId(resultSet.getInt("lexiconID"));
				posting.setTf(resultSet.getInt("tf"));
				posting.setDocID(resultSet.getInt("docID"));
				// 把posting加到postingList
				postingList.add(posting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postingList;
	}
	
	
	/*
	 * 根据lexiconId获得IDF的值
	 */
	public double getIdfFromLex(int lexId) {
		String cmdString = "select IDF from lexcion where LexiconID='"+lexId+"'";
		Statement ste;
		double IDF = 0;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while (resultSet.next()) {
				IDF=resultSet.getDouble("IDF");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IDF;
	}

	/*
	 * 根据lexiconId获得IDF的值
	 */
	public Doc getDocById(int docId) {
		
		Doc doc=new Doc();
		String cmdString = "select * from doc where doc.DocID='"+docId+"'";
		Statement ste;
	   
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while (resultSet.next()) {
				String docText=resultSet.getString("DocText");
				int docID=resultSet.getInt("DocID");
				String authorName=resultSet.getString("authorName");
				doc.setDocCon(docText);
				doc.setAuthorName(authorName);
				doc.setDocID(docID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
}
