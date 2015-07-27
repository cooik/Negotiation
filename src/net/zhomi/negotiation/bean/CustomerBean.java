package net.zhomi.negotiation.bean;

import java.io.Serializable;

public class CustomerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String tel;
	private String name;
	private String pic_url;
	

	public CustomerBean() {
		super();
	}

	public CustomerBean(String id, String tel, String name, String pic_url) {
		super();
		this.id = id;
		this.tel = tel;
		this.name = name;
		this.pic_url = pic_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String icLauncher) {
		this.pic_url = icLauncher;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	

}
