package com.qyt.k_means;
public class DistanceCompute {
    /**
     * 求欧式距离
     */
    public double getEuclideanDis(Point p1, Point p2) {
        double count_dis = 0;
        double[] p1_local_array = p1.getlocalArray();
        double[] p2_local_array = p2.getlocalArray();
        System.out.println(p1_local_array.length);
    	System.out.println(p2_local_array.length);
    	System.out.println("------------------------");
        if (p1_local_array.length != p2_local_array.length) {
            throw new IllegalArgumentException("length of array must be equal!");
        }

        for (int i = 0; i < p1_local_array.length; i++) {
            count_dis += Math.pow(p1_local_array[i] - p2_local_array[i], 2);
        }
      return Math.sqrt(count_dis);
//        double d1=getNum(p1_local_array,p2_local_array);
//        double d2=getNum1(p1_local_array);
//        double d3=getNum1(p2_local_array);
//        return d1/(d2*d3);
       
    }
    /**
     *x*x1+y*y1... 
     * @return
     */
//    public static double getNum(double[] p1_local_array ,double[] p2_local_array) {
//    	double temp=0;
//    	for(int i=0;i<p1_local_array.length;i++) {
//    		temp+=p1_local_array[i]*p2_local_array[2];
//    	}
//    	return temp;
//    }
//    /**
//     *开方 
//     */
//    public static double getNum1(double[] p1_local_array) {
//    	double temp=0;
//    	for(int i=0;i<p1_local_array.length;i++) {
//    		temp=p1_local_array[i]*p1_local_array[i];
//    	}
//    	return Math.sqrt(temp);
//    }
    
}
