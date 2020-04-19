package DataAnalyzer;

public class dataAnalyzer {
	public void mainAnalyzer(){
		//初始化数据库
		Global.dBer.connectDB();
		
		zplKMeans kMeans;
		//把
		kMeans = new zplKMeans(5);
		kMeans.readVectorSet();//
		kMeans.initRandom();
		kMeans.start();
		kMeans.showAll();
		kMeans.calculate();
		Global.dBer.closeDB();
	}
	
	
	
}
