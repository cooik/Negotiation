package net.zhomi.negotiation.model;

public class UserInfo {
	/** 用户名 **/
	private String name;
	/** 密码 **/
	private String pwd;
	/** 用户头像地址 **/
	private String avtUrl;
	/**登录成功后的md5值**/
	private String md5;

	public UserInfo() {
		super();
	}

	public UserInfo(String name, String pwd, String avtUrl,String md5) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.avtUrl = avtUrl;
		this.md5=md5;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAvtUrl() {
		return avtUrl;
	}

	public void setAvtUrl(String avtUrl) {
		this.avtUrl = avtUrl;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
