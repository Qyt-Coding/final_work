package DataMining;

import java.util.List;

import com.bean.WeiboDoc;
/**
 *处理出来的数据集合 
 */
public class Page {
	
	public List<WeiboDoc> list;
	
	public int count;

	public List<WeiboDoc> getList() {
		return list;
	}

	public void setList(List<WeiboDoc> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
