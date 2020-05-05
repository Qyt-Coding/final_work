package DataMining;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bean.WeiboDoc;

public class splitSystem {
	
	public class wordCount{
		public class wordAndCount{
			public int count;
			public String word;
			public wordAndCount(String w,int c){
				count = c;
				word = w;
			}
		}
		
		public List<wordAndCount> wordlist ;
		public wordCount(){
			wordlist = new ArrayList<wordAndCount>();
		}
		public void addValue(String word,int count){
			wordlist.add(new wordAndCount(word, count));
		}
 
		/**
		 * qyt
		 */
		public void begin(Doc doc){
			List<String> tmpList=new ArrayList<String>();
			IKSegmenter smartSegmenter = new IKSegmenter(new StringReader(doc.getDocCon()), true);
			System.out.print("智能分词结果：");
			Lexeme next;
			try {
				while ((next = smartSegmenter.next()) != null) {
					//System.out.println(next.getLexemeText() + " ");
					if(Global.markNolex(next.getLexemeText()) == false){//判断该词是否是常用词
						tmpList.add(next.getLexemeText());//term.toString()分词结果
					}
				}
				
				for(String word:tmpList) {
					boolean match=false;
					for(int i=0;i<wordlist.size();i++)
					{
						//wordlist一开始也是为0的，如果这个词是第一次插入进来的话，那么match就为false
						if(word.equals(wordlist.get(i).word))
						{
							wordlist.get(i).count++;
							match=true;
							break;
						}
					}
					if(match==false)//把重复的去掉，count是TF
					{
						wordlist.add(new wordAndCount(word,1));
					}
				}
				
				
				for(wordAndCount w: wordlist){
					Global.dBer.addLiexiconAndPosting(w.word, w.count,doc.getDocID());
				}
				
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++终结");
				
				//关闭TokenStream（关闭StringReader）
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				//释放TokenStream的所有资源
		    }
		}
	}
	
	private Doc _target;
 
	/**
	 * qyt
	 */
	public void myBeginSplit(WeiboDoc weibo) {
		
		Doc _target=new Doc();
		_target.setDocID(Integer.valueOf(weibo.getId()));
		_target.setDocCon(weibo.getText());
		wordCount wCount = new wordCount();
		wCount.begin(_target);
	}
	
	
}