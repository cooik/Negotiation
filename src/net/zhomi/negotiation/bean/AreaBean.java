package net.zhomi.negotiation.bean;
/**
 * 地区bean
 * @author yangshouzhi
 *
 */
public class AreaBean {
	private String name;
	private String id;
	
	public AreaBean() {
		super();
	}

	public AreaBean(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
