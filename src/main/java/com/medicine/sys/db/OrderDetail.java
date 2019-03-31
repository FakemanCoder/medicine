package com.medicine.sys.db;

import java.util.Date;

/**
 * order_detail实体类
 * 
 * @author 
 *
 */
public class OrderDetail {
	private int order_detail_id; 
	private int order_sum_id; 
	private String status; 
	private int num; 
	private int medicine_id; 
	private int warehouse_user_id; 
	private int createby; 
	private Date createdate; 
	private int updateby; 
	private Date updatedate;
	
	public int getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(int order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public int getOrder_sum_id() {
		return order_sum_id;
	}

	public void setOrder_sum_id(int order_sum_id) {
		this.order_sum_id = order_sum_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(int medicine_id) {
		this.medicine_id = medicine_id;
	}
	public int getWarehouse_user_id() {
		return warehouse_user_id;
	}

	public void setWarehouse_user_id(int warehouse_user_id) {
		this.warehouse_user_id = warehouse_user_id;
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
		return "OrderDetail [order_detail_id=" + order_detail_id + ", order_sum_id=" + order_sum_id + ", status="
				+ status + ", num=" + num + ", medicine_id=" + medicine_id + ", warehouse_user_id=" + warehouse_user_id
				+ ", createby=" + createby + ", createdate=" + createdate + ", updateby=" + updateby + ", updatedate="
				+ updatedate + "]";
	}
}
