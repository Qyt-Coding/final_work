package DataMining;

import java.util.List;

import org.junit.Test;

import com.bean.WeiboDoc;

/**
 * 数据处理
 */
public class dateProccess {
	
	@Test
	public void mainProccess() {
		dateProccess proccess=new dateProccess();
		Global.dBer.connectDB();
		Global.dBer.initValue();
		Page page=Global.dBer.getPage();
		List<WeiboDoc>  weiboList=page.getList();
		for(WeiboDoc weibo:weiboList) {
			Global.split.setDoc(weibo.getText());
			Global.split.myBeginSplit(weibo);//分词不错
		}
		//分词
		Global.dBer.closeDB();
	}
}
