package com.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.second.DB.Utilsqyt;
import com.second.DB.updateVector;

public class testMain {

	    public static void main(String[] args) {
	        Utilsqyt.getInstance().connectDB();//开始连接数据库
	        updateVector up=new updateVector();
	        List<Point> list=up.initDocVector();
	        List<double[]> origin_date=new ArrayList<double[]>();
	        for(Point p:list) {
	        	origin_date.add(p.getlocalArray());
	        }

	        KMeansRun kRun =new KMeansRun(20, origin_date);

	        Set<Cluster> clusterSet = kRun.run();
	        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
	        for (Cluster cluster : clusterSet) {
	            System.out.println(cluster);
	        }
	    }
}
