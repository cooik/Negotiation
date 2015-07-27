package net.zhomi.negotiation.bean;

public class BusinessMomentBean {
	private String date;
	private String imgUrl;
	private String title;
	private String id;
	public BusinessMomentBean() {
		super();
	}
	public BusinessMomentBean(String date, String imgUrl, String title,
			String id) {
		super();
		this.date = date;
		this.imgUrl = imgUrl;
		this.title = title;
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
