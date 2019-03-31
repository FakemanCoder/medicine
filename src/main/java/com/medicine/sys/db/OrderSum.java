package com.medicine.sys.db;

import java.util.Date;

/**
 * order_sum实体类
 * 
 * @author 
 *
 */
public class OrderSum {
	private int order_sum_id; 
	private double price; 
	private int createby; 
	private Date createdate; 
	private int updateby; 
	private Date updatedate;
	public int getOrder_sum_id() {
		return order_sum_id;
	}
	public void setOrder_sum_id(int order_sum_id) {
		this.order_sum_id = order_sum_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	@Override
	public String toString() {
		return "OrderSum [order_sum_id=" + order_sum_id + ", price=" + price + ", createby=" + createby
				+ ", createdate=" + createdate + ", updateby=" + updateby + ", updatedate=" + updatedate + "]";
	}
}
