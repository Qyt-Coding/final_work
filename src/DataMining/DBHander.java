package DataMining;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.WeiboDoc;

import weibo4j.model.Status;

public class DBHander {

	private int docCount; // 文档的总数 初始化的时候就给他们赋值了
	private int lexCount; // lex表示分词后总数
	private Connection conn;
	private Statement statement;
	private PreparedStatement ptmt;

	/**
	 * 数据库处理者
	 */
	public DBHander() {
		docCount = 0;
		lexCount = 0;
		conn = null;
		statement = null;
	}

	public String convertCharset(String old) {
		try {
			return new String(old.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Connection getCon() {
		return conn;
	}
	/**
	 *usedlist表 
	 */
	public boolean initUsedList() {
		String cmdString = "select useid from usedlist;";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				String id = resultSet.getString("useid");
				Global.usedIDList.add(id);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	public boolean insertUsedList(String id) {
		String cmdString = "select * from usedlist where useid='" + id + "';";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			if (resultSet.next()) {
				// 如果存在这条数据，那么不走下面的步骤
				return true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cmdString = "insert into usedlist(useid) values('" + id + "');";
		statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(cmdString);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}
	/**
	 * 初始化数据库
	 */
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
	 * 初始化文本和lexcion
	 */
	public void initValue() {
		// statement.executeUpdate(cmdString);
		try {
			statement = conn.createStatement();
			ResultSet set = null;
			String cmdString = "select count(*) from doc";

			set = statement.executeQuery(cmdString);
			if (set != null) {
				int a = 0;
				if (set.next())
					a = set.getInt(1);
				docCount = a;

				a = 0;
				cmdString = "select count(*) from lexcion";
				set = statement.executeQuery(cmdString);
				if (set.next())
					a = set.getInt(1);
				lexCount = a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int getDocCount() {
		return docCount;
	}

	public boolean closeDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean addDoc(String s, int i) {
		//更新数据库

		String cmdString = "insert into doc(docid,DocText) values(" + i + ",'" + convertCharset(s) + "');";
		try {
			statement = conn.createStatement();
			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			// e.printStackTrace();

		}
		return true;
	}

	public boolean addDoc(Status aDocCon) {
		Doc tmpDoc = new Doc();
		statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		docCount++;
		tmpDoc.setDocID(docCount);

		if (aDocCon.getRetweetedStatus() == null)
			tmpDoc.setDocCon(aDocCon.getText());
		else {
			tmpDoc.setDocCon(aDocCon.getRetweetedStatus().getText());
		}

		// 更新数据库
		String cmdString = "insert into doc(DocID,DocText) values(" + tmpDoc.getDocID() + ",'"
				+ convertCharset(tmpDoc.getDocCon()) + "');";
		try {

			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			docCount--;
			return true;

		}
		Global.docList.add(tmpDoc);
		return true;
	}

	/**
	 * qyt 新增方法
	 * 
	 * @param aLexicon
	 * @param tf
	 * @param docID
	 * @return
	 */
	public boolean addDocqyt(WeiboDoc weiboDoc) {
		Doc tmpDoc = new Doc();
		
		docCount++;
		// 这个是id，自动增加用的
		tmpDoc.setDocID(docCount);
		//aDocCon.setId(docCount);
		// 转发的那个地方
//		if (weiboDoc.getRetweetedStatus() == null)
//			tmpDoc.setCon(weiboDoc.getText());
//		else {
//			tmpDoc.setCon(weiboDoc.getRetweetedStatus().getText());
//		}

		// 更新数据库
		//String cmdString = "insert into doc(DocID,DocText,authorId,authorName) values(" +  docCount + ","
		//		+ "'"
		//		+ convertCharset(weiboDoc.getText()) + "','"+weiboDoc.getUser().getId()+"','"+weiboDoc.getUser().getScreen_name()+"');";
//		tmpDoc.setDocID(docCount);
//		tmpDoc.setDocCon(weiboDoc.getText());
//		tmpDoc.setAuthorId(weiboDoc.getUser().getId());
//		tmpDoc.setAuthorName(weiboDoc.getUser().getScreen_name());
		//------------------------
		String cmdString="insert into doc(DocID,DocText,authorId,authorName,reposts_count,comments_count,attitudes_count) "
				+ "values(?,?,?,?,?,?,?)";
		try {
			ptmt=conn.prepareStatement(cmdString);
			ptmt.setInt(1, docCount);
			ptmt.setString(2, convertCharset(weiboDoc.getText()));
			ptmt.setString(3, weiboDoc.getUser().getId());
			ptmt.setString(4,weiboDoc.getUser().getScreen_name());
			ptmt.setInt(5, weiboDoc.getRepostsCount());
			ptmt.setInt(6, weiboDoc.getCommentsCount());
			ptmt.setInt(7, weiboDoc.getAttitudesCount());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tmpDoc.setDocID(docCount);
		tmpDoc.setDocCon(weiboDoc.getText());
		tmpDoc.setAuthorId(weiboDoc.getUser().getId());
		tmpDoc.setAuthorName(weiboDoc.getUser().getScreen_name());
		
		try {
			ptmt.execute();
			//statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("----------------出错了");
			System.out.println(e.toString());
			System.out.println("cmdString-->"+cmdString);
			docCount--;
			return true;

		}
		Global.docList.add(tmpDoc);
		return true;
	}
 
	/**
	 * qyt
	 */
	public Liexicon IslexExist(String word) {
		Liexicon sLiexicon = null;
		String cmdString = "select * from lexcion where lexicon='" + convertCharset(word) + "';";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				double idf = Double.parseDouble(resultSet.getString("idf"));
				int lexID = resultSet.getInt("lexiconID");
				int docF = resultSet.getInt("docF");// 单词出现次数
				//docF=docF+tf;
				sLiexicon = new Liexicon(lexID, word, docF);
				sLiexicon.setIDF(idf);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sLiexicon;
	}
	/**
	 *获取到分页 
	 */
	public Page getPage() {
		String sql="select  *  from doc";
		String sql1="select  count(*)  from doc";
		Page page=new Page();
		List<WeiboDoc> list=new ArrayList<WeiboDoc>();
		try {
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				 WeiboDoc weibo=new WeiboDoc();
				 weibo.setId(rs.getInt("DocID")+"");
				 weibo.setText(rs.getString("DocText"));
				 list.add(weibo);
			}
			statement=null;
			statement = conn.createStatement();
			
			ResultSet rs2 =statement.executeQuery(sql1);
			int count=0;
			if(rs2.next()) {
				count=rs2.getInt(1);
			}
			page.setList(list);
			page.setCount(count);
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;
	}
	
	
	public boolean addLiexiconAndPosting(String word ,int tf ,int docId) {//这里的Tf是
		
		Liexicon s=null;
		//判断有没有这个单词
		s=isLexcion(word);
		if(s==null){//说明表里没有这个单词
			// 新的单词
		lexCount++;
		String cmdString=null;
		Liexicon sLiexicon = new Liexicon(lexCount, word, 0);// lexCount这个是，自动增长的，
		sLiexicon.updatedocF();//更新IDF的值
		// 加入词典
		cmdString = "insert into lexcion(lexiconID,IDF,lexicon,docF) values(" + sLiexicon.getID() + ","
				+ sLiexicon.getIDF() + ",'" + convertCharset(sLiexicon.getLexicon()) + "'," + sLiexicon.getDocF()
				+ ");";
		try {
			System.out.println(cmdString);
			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
			return false;
		}

		
		cmdString = null;
		// 建立倒排列表
		cmdString = "insert into postingfile(lexiconID) values(" + lexCount + ");";
		try {
			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		cmdString = null;
		cmdString = "insert into postingitem(lexiconID,docid,tf) values(" + sLiexicon.getID() + "," + docId + ","
				+ tf + ");";
		try {
			statement.executeUpdate(cmdString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		}else {
			try {
				
				//开启事务
				conn.setAutoCommit(false);
				String cmdString =null;
				s.updatedocF();
				cmdString = "update lexcion set idf=" + s.getIDF()+tf + ",docf=" + s.getDocF() + " where lexiconID="
						+ s.getID() + ";";

				statement.executeUpdate(cmdString);
				
				cmdString = null;
				// 更新倒排列表
				cmdString = "insert into postingitem(lexiconID,docid,tf) values(" + s.getID() + "," + docId + "," + tf+");";
				statement.executeUpdate(cmdString);
				cmdString = null;
			}  catch (SQLException e1) {
				e1.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}finally {
				try {
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public Liexicon isLexcion(String word) {
		Liexicon sLiexicon = null;
		String cmdString = "select * from lexcion where lexicon='" + convertCharset(word) + "';";
		statement = null;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(cmdString);
			while (resultSet.next()) {
				double idf = Double.parseDouble(resultSet.getString("idf"));
				int lexID = resultSet.getInt("lexiconID");
				int docF = resultSet.getInt("docF");// 单词出现次数
				sLiexicon = new Liexicon(lexID, word, docF);
				//sLiexicon.setIDF(idf);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sLiexicon;
	}
	

}