package net.zhomi.negotiation.bean;

import java.io.Serializable;
/**
 * 客户详情类
 * @author tanjin
 *
 */
public class DetailsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7569236958441041787L;
	private String id;
	private String time;
	private String location;
	private String note;
	public DetailsBean() {
		super();
	}
	public DetailsBean(String id, String time, String location, String note) {
		super();
		this.id = id;
		this.time = time;
		this.location = location;
		this.note = note;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
