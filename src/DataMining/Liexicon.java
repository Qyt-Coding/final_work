package DataMining;
public class Liexicon {
	private int LexID;	//文档id
	protected String _lexicon;	//这个是text
	private int docF;	//单词出现的频率
	private double IDF;

	public Liexicon(){
		_lexicon = null;
		docF = 0;
		IDF = (Math.log(Global.dBer.getDocCount()/1)/Math.log(2)) +0.5;
		LexID = 0;
	}
	public Liexicon(int lexID,String lex,int dof){
		_lexicon = lex;
		LexID = lexID;
		docF = dof;//docf
		IDF = (Math.log(Global.dBer.getDocCount()/1)/Math.log(2)) +0.5;
		updatedocF();
	}
	public void setID(int num){
		LexID = num;
	}
	public int getID(){
		return LexID;
	}
	public void setLexicon(String aLexicon) {
		_lexicon = aLexicon;
	}

	public String getLexicon() {
		return this._lexicon;
	}
	public void computeIDF(double allDOcCount){
		IDF = (Math.log(allDOcCount/docF)/Math.log(2))+0.5;
	}
	public void updatedocF(){
		//qyt
		docF++;
		computeIDF(Global.dBer.getDocCount());
	}
	public int getDocF(){
		return (int) docF;
	}
	public double getIDF(){
		return IDF;
	}
	public void setIDF(double idf){
		IDF = idf;
	}
}