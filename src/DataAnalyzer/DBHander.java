package DataAnalyzer;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.WeiboDoc;

public class DBHander {
	private int docCount ;//文档的数量
	private int lexCount ;//单词的数量
	private Connection conn;
	
	/**
	 * 数据库处理者
	 */
	public DBHander(){
		docCount = 0;
		lexCount = 0;
		conn = null;
	}
	public String convertCharset(String old){
		try {
			String strUtf = new String(old.getBytes(),"UTF-8");
			String strgbString = new String(old.getBytes(),"gb2312");
			System.out.println("utf:"+strUtf);
			System.out.println("gb:"+strgbString);
			return new String(old.getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Connection getCon(){
		return conn;
	}
	public boolean connectDB(){
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
	
			
			if(!conn.isClosed()){
				System.out.println("Succeeded connecting to the Database!");
				getWeidu();
				return true;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		return false;
	}
	public int getDocCount(){
		return docCount;
	}
	public boolean closeDB(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void getWeidu(){
		String cmdString = "select * from lexcion";
		Statement ste;
		try {
			Global.weidu = 0;
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while(resultSet.next()){
				Global.weidu ++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lexCount = Global.weidu;
	}
	public double getIDf(int lexID){
		double idf = 0;
		String idfString;
		String cmdString = "select IDF from lexcion where lexiconID="+lexID+";";
		Statement ste;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			if(resultSet.next()){
				idfString = resultSet.getString("IDF");
				idf =Double.parseDouble(idfString);
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idf;
	}
	public boolean zeroVector(double[] s){
		for(int i =0; i< lexCount;i++){
			if(s[i] != 0.0){
				return false;
			}
		}
		return true;
	}
	/**
	 * 把文档进行向量化
	 */
	public docVector buildDocVector(int docID){
		String cmdString = "select * from postingitem where docID="+ docID+";";
		ResultSet resultSet = null;
		docVector doc = new docVector(docID);
		double[] value = new double[lexCount];//为什么lexCount有值
		int lexiconID = 0;
		int tf;
		double idf;
		for(int i =0; i<lexCount;i++){//给数组赋值
			value[i]= 0; 
		}
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				lexiconID = resultSet.getInt("lexiconID");
				tf = resultSet.getInt("tf");//tf是这个单词在某个文档出现的次数
				idf = getIDf(lexiconID);//其实就是从lexcion表里取idf这个值
				value[lexiconID -1] = tf*idf;
			}
		} catch (SQLException e) {
			System.out.println("lex ID:"+lexiconID);
		}
		if(zeroVector(value) == false){
			doc.setitemNum(lexCount);
			doc.setValue(value);
		}
		else {
			return null;
		}
		
		return doc;
	}
	
	public TermVector buildTermVector(){
		TermVector vector = new TermVector();
		docVector tmp = null;//
		Global.docNum = 0;//0
		String cmdString = "select docID from doc";
		Statement ste;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while(resultSet.next()){
				int docID = resultSet.getInt("docID");
				tmp = buildDocVector(docID);//doc的矢量  //postman里docID等于的数组
				
				if(tmp != null){
					vector.addVector(tmp);
					Global.docNum ++;
					//System.out.println("docnum"+Global.docNum);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vector;
	}
	
	public String getDoc(int docID){
		String cmdString = "select docText from doc where docid="+docID+";";
		Statement ste;
		String temp = null;
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while(resultSet.next()){
				temp = resultSet.getString("docText");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}
	/**
	 *获得整个doc 
	 */
	public WeiboDoc getDocById(int docID){
		String cmdString = "select * from doc where docid="+docID+";";
		Statement ste;
		WeiboDoc weibo=new WeiboDoc();
		try {
			ste = conn.createStatement();
			ResultSet resultSet = ste.executeQuery(cmdString);
			while(resultSet.next()){
				
				weibo.setId(resultSet.getString("docId"));
				weibo.setCommentsCount(resultSet.getInt("attitudes_count"));
				weibo.setRepostsCount(resultSet.getInt("reposts_count"));
				weibo.setCommentsCount(resultSet.getInt("comments_count"));
				weibo.setText(resultSet.getString("docText")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return weibo;
	}
}
