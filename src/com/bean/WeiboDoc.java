package com.bean;

import weibo4j.model.Status;

public class WeiboDoc  implements Comparable<WeiboDoc>{
	
	String createdAt;
	String id;
	String  text;
	Long rank;
	String originalPic;  //原始图片
	Integer repostsCount;  //转发数
	Integer commentsCount; //评论数
	Integer attitudesCount; //点赞数
	WeiboDoc retweetedStatus=null; //如果是转发的就有这个字段，不是转发的话这个字段就为null
	User user=new User();
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public String getOriginalPic() {
		return originalPic;
	}
	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}
	public Integer getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(Integer repostsCount) {
		this.repostsCount = repostsCount;
	}
	public Integer getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}
	public Integer getAttitudesCount() {
		return attitudesCount;
	}
	public void setAttitudesCount(Integer attitudesCount) {
		this.attitudesCount = attitudesCount;
	}
	public WeiboDoc getRetweetedStatus() {
		return retweetedStatus;
	}
	public void setRetweetedStatus(WeiboDoc retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "WeiboDoc [createdAt=" + createdAt + ", id=" + id + ", text=" + text + ", rank=" + rank
				+ ", originalPic=" + originalPic + ", repostsCount=" + repostsCount + ", commentsCount=" + commentsCount
				+ ", attitudesCount=" + attitudesCount + ", retweetedStatus=" + retweetedStatus + ", user=" + user
				+ "]";
	}
	public int compareTo(WeiboDoc o) {
		return (int)(o.getRank()-this.getRank());
	}
	
}
