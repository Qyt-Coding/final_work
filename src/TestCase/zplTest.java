package TestCase;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import DataAnalyzer.dataAnalyzer;
import DataMining.*;

public class zplTest {

	//case1 测试数据获取模块
	@Test
	public void testMining() throws Exception {
		dataMining testdata = new dataMining();
		testdata.mainDataMining();
	}
}
