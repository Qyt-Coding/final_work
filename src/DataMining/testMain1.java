package DataMining;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class testMain1 {

	public static void main(String args[]) {
		Analyzer analyzer = new IKAnalyzer(true);
		StringReader reader = new StringReader("IKAnalyzer 是一个开源的，基亍 java 语言开发的轻量级的中文分词工具包。从 2006 年 12\r\n" + 
				"月推出 1.0 版开始， IKAnalyzer 已经推出了 3 个大版本。");
		List<String> tmpList=new ArrayList<String>();
		//获取Lucene的TokenStream对象
	    TokenStream ts = null;
		try {
			ts = analyzer.tokenStream("myfield", reader);
 
		    //获取词元文本属性
		    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);

		    
		    
		    //重置TokenStream（重置StringReader）
			ts.reset(); 
			//迭代获取分词结果,加入到tmplist中待处理
			while (ts.incrementToken()) {//遍历分词后的数组
				//if(Global.markNolex(term.toString()) == false){
					System.out.print(term.toString()+"  | ");
				//}
			}
	}catch(Exception e) {
		}
	}

}
