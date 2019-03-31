package com.medicine.sys.db;

import java.util.Date;

public class Medicine {

	private int medicine_id; 
	private String cn_name; 
	private double in_price; 
	private double pkg_price; 
	private String description; 
	private String is_release; 
	private String func; 
	private int content_num; 
	private String content_unit; 
	private int createby; 
	private Date createdate; 
	private int updateby; 
	private Date updatedate;

	public int getMedicine_id() {
		return medicine_id;
	}
	public void setMedicine_id(int medicine_id) {
		this.medicine_id = medicine_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public int getCreateby() {
		return createby;
	}
	public void setCreateby(int createby) {
		this.createby = createby;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getUpdateby() {
		return updateby;
	}
	public void setUpdateby(int updateby) {
		this.updateby = updateby;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getCn_name() {
		return cn_name;
	}
	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}
	public double getIn_price() {
		return in_price;
	}
	public void setIn_price(double in_price) {
		this.in_price = in_price;
	}
	public double getPkg_price() {
		return pkg_price;
	}
	public void setPkg_price(double pkg_price) {
		this.pkg_price = pkg_price;
	}
	public String getIs_release() {
		return is_release;
	}
	public void setIs_release(String is_release) {
		this.is_release = is_release;
	}
	public int getContent_num() {
		return content_num;
	}
	public void setContent_num(int content_num) {
		this.content_num = content_num;
	}
	public String getContent_unit() {
		return content_unit;
	}
	public void setContent_unit(String content_unit) {
		this.content_unit = content_unit;
	}
	@Override
	public String toString() {
		return "Medicine [medicine_id=" + medicine_id + ", cn_name=" + cn_name + ", in_price=" + in_price + ", pkg_price="
				+ pkg_price + ", description=" + description + ", is_release=" + is_release + ", func=" + func
				+ ", content_num=" + content_num + ", content_unit=" + content_unit + ", createby=" + createby
				+ ", createdate=" + createdate + ", updateby=" + updateby + ", updatedate=" + updatedate + "]";
	}
}
