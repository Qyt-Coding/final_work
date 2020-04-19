package DataAnalyzer;

import java.util.ArrayList;
import java.util.List;

import com.bean.WeiboDoc;

public class WeiBoVo {
	int id;
	List<WeiboDoc> weiboList=new ArrayList<WeiboDoc>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<WeiboDoc> getWeiboList() {
		return weiboList;
	}
	public void setWeiboList(List<WeiboDoc> weiboList) {
		this.weiboList = weiboList;
	}
	@Override
	public String toString() {
		return "WeiBoVo [id=" + id + ", weiboList=" + weiboList + ", getId()=" + getId() + ", getWeiboList()="
				+ getWeiboList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	

}
