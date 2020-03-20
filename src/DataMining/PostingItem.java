package DataMining;

public class PostingItem {
	int id;  
	int tf;   //频率
	int lexiconId;  //
	int docID;//稿件id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTf() {
		return tf;
	}
	public void setTf(int tf) {
		this.tf = tf;
	}
	public int getLexiconId() {
		return lexiconId;
	}
	public void setLexiconId(int lexiconId) {
		this.lexiconId = lexiconId;
	}
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	@Override
	public String toString() {
		return "PostingItem [id=" + id + ", tf=" + tf + ", lexiconId=" + lexiconId + ", docID=" + docID + ", getId()="
				+ getId() + ", getTf()=" + getTf() + ", getLexiconId()=" + getLexiconId() + ", getDocID()=" + getDocID()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
