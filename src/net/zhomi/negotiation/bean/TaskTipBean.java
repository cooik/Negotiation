package net.zhomi.negotiation.bean;

/**
 * 任务提醒bean
 * 
 * @author yangshouzhi
 * 
 */
public class TaskTipBean {
	private String name;
	private String note;
	private String date;
	private String id;

	public TaskTipBean() {
		super();
	}

	public TaskTipBean(String id,String name, String note, String date) {
		super();
		this.id=id;
		this.name = name;
		this.note = note;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
