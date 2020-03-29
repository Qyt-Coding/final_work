package DataAnalyzer;

public class dataAnalyzer {
	public void mainAnalyzer(){
		//初始化数据库
		Global.dBer.connectDB();
		zplKMeans kMeans;
		//把
		kMeans = new zplKMeans(20);
		kMeans.readVectorSet();//主要是把文档进行向量化
		kMeans.initRandom();//list cluster len:1
		kMeans.start();
		kMeans.showAll();
		kMeans.getAllClusterInfo();
		Global.dBer.closeDB();
	}
}
