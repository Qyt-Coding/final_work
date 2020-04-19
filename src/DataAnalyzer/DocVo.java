package DataAnalyzer;

public class DocVo {
	protected int DocID;
	protected String DocCon;
	protected String authorId;
	protected String authorName;
	protected Integer reposts_count;
	protected Integer comments_count;
	protected Integer attitudes_count;
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
	public Integer getReposts_count() {
		return reposts_count;
	}
	public void setReposts_count(Integer reposts_count) {
		this.reposts_count = reposts_count;
	}
	public Integer getComments_count() {
		return comments_count;
	}
	public void setComments_count(Integer comments_count) {
		this.comments_count = comments_count;
	}
	public Integer getAttitudes_count() {
		return attitudes_count;
	}
	public void setAttitudes_count(Integer attitudes_count) {
		this.attitudes_count = attitudes_count;
	}
	@Override
	public String toString() {
		return "DocVo [DocID=" + DocID + ", DocCon=" + DocCon + ", authorId=" + authorId + ", authorName=" + authorName
				+ ", reposts_count=" + reposts_count + ", comments_count=" + comments_count + ", attitudes_count="
				+ attitudes_count + ", getDocID()=" + getDocID() + ", getDocCon()=" + getDocCon() + ", getAuthorId()="
				+ getAuthorId() + ", getAuthorName()=" + getAuthorName() + ", getReposts_count()=" + getReposts_count()
				+ ", getComments_count()=" + getComments_count() + ", getAttitudes_count()=" + getAttitudes_count()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
