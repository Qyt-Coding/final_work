package com.second.DB;

import java.util.ArrayList;
import java.util.List;

import com.DB.Globalqyt;
import com.second.Point;

import DataMining.Doc;
import DataMining.PostingItem;

public class updateVector {
	List docVectorList=new ArrayList<Point>();
	
	
	public List<Point>  initDocVector() {
		List<Doc> docList=Utilsqyt.getInstance().getDocList();
		
		List<Point> pointList=new ArrayList<Point>();
		//开始对doc进行向量话
		for(Doc doc:docList) {
			double[] value=new double[this.getLexNum()];
			List<PostingItem> postingItemList=Utilsqyt.getInstance().getPostingItemByDocId(doc.getDocID());
			//List<Doc> lexDoc=new ArrayList<Doc>();
			
			
			for(int i=0;i<postingItemList.size();i++) {
				PostingItem  postingItem =postingItemList.get(i);
				int tf =postingItem.getTf();
				double idf=Utilsqyt.getInstance().getIdfFromLex(postingItem.getLexiconId());
				
				value[postingItem.getLexiconId()-1]=tf*idf;
			}		
			Point point=new Point(doc.getDocID(),value);
			pointList.add(point);
		}
		return pointList;
	}
	
	public int getLexNum() {
		return Utilsqyt.getInstance().getLexNum();
	}
}
