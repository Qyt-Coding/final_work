package DataAnalyzer;

public class zplCluster {
	private docVector _mean;
	private int clusterNum;
	private TermVector _vector;

	
	public zplCluster(){
		_mean = new docVector(-1);
		_vector = null;
		clusterNum = 0;
	}
	public int getNum(){
		return clusterNum;
	}
	public boolean addDoc(docVector v){
		if(v !=null){
			_vector.addVector(v);
			clusterNum ++;
			return true;
		}
		return false;
			
	}
	public docVector getMean(){
		return _mean;
	}
	public TermVector getTerm(){
		return _vector;
	}
	public void UpdateMean() {//重新算坐标
		
		int weidu = Global.weidu;
		double[] value = new double[weidu];
		for(int i = 0; i< weidu;i++){
			double tmp = 0;
			for(docVector v:_vector.getList()){
				tmp += v.getValue()[i];
			}
			value[i]= tmp/(double)weidu;//全部x相加
		}
		_mean.setValue(value);//设坐标
		try{
			if(_mean.computeLength() ==0)
				throw new Exception();
			
		}catch (Exception e) {
			System.out.println("list len:"+_vector.getList().size());
			if(_vector.getList().size() == 0){
				System.out.println("+++++");
			}
		}
		
		
	}


	public boolean setCluster(TermVector _v) {
		if(_v != null){
			_vector = _v;
			System.out.println("list cluster len:"+_v.getList().size());
			UpdateMean();
			return true;
		}
		return false;
	}
	
	/**
	 * 当误差小于0.01的时候，停止迭代
	 * doc1比较对象
	 * c误差值
	 */
	public boolean isPass(docVector doc1,double c) {
		double a=this.getMean().computeLength();
		double b=doc1.computeLength();
		if(a-b>=c) {
			System.out.println("a-b:"+a + - + b);
			return false;//如果大于，继续迭代
		}
		return true;
	}
	
}