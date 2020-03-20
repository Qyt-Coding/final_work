package DataMining;

public class Doc {
	protected int DocID;
	protected String DocCon;
	protected String authorId;
	protected String authorName;
	public int getDocID() {
		return DocID;
	}
	public void setDocID(int docID) {
		DocID = docID;
	}
	public String getDocCon() {
		return DocCon;
	}
	public void setDocCon(String docCon) {
		DocCon = docCon;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	@Override
	public String toString() {
		return "Doc [DocID=" + DocID + ", DocCon=" + DocCon + ", authorId=" + authorId + ", authorName=" + authorName
				+ ", getDocID()=" + getDocID() + ", getDocCon()=" + getDocCon() + ", getAuthorId()=" + getAuthorId()
				+ ", getAuthorName()=" + getAuthorName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
