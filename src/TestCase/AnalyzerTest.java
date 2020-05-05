package TestCase;

import org.junit.Test;

import DataAnalyzer.dataAnalyzer;

public class AnalyzerTest {

	//第三个模块
	@Test
	public void testAnalyzer(){
		dataAnalyzer ss = new dataAnalyzer();
		ss.mainAnalyzer();
	}

}
