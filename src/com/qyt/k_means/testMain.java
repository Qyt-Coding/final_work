package com.qyt.k_means;
import java.util.ArrayList;
import java.util.Set;

import com.DB.DBhanderqyt;
import com.DB.Globalqyt;

public class testMain {

	    public static void main(String[] args) {
//	        ArrayList<double[]> dataSet = new ArrayList<double[]>();
//	        dataSet.add(new double[] { 1, 2, 3 });
//	        dataSet.add(new double[] { 3, 3, 3 });
//	        dataSet.add(new double[] { 3, 4, 4});
//	        dataSet.add(new double[] { 5, 6, 5});
//	        dataSet.add(new double[] { 8, 9, 6});
//	        dataSet.add(new double[] { 4, 5, 4});
//	        dataSet.add(new double[] { 6, 4, 2});
//	        dataSet.add(new double[] { 3, 9, 7});
//	        dataSet.add(new double[] { 5, 9, 8});
//	        dataSet.add(new double[] { 4, 2, 10});
//	        dataSet.add(new double[] { 1, 9, 12});
//	        dataSet.add(new double[] { 7, 8, 112});
//	        dataSet.add(new double[] { 7, 8, 4});
	    	Globalqyt.dBer.connectDB();
	    	updateVector up=new updateVector();
	    	
	        KMeansRun kRun =new KMeansRun(20, up.initDocVector());

	        Set<Cluster> clusterSet = kRun.run();
	        System.out.println("单次迭代运行次数："+kRun.getIterTimes());
	        for (Cluster cluster : clusterSet) {
	        	System.out.println("++++"+cluster.getId());
	            System.out.println(cluster);
	        }
	    }
}
