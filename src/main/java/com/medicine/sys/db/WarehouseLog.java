package com.medicine.sys.db;

import java.util.Date;

/**
 * warehouse_log实体类
 * 
 * @author 
 *
 */
public class WarehouseLog {
	private int warehouse_log_id; 
	private int num; 
	private int medicine_id; 
	private double price; 
	private String datafrom; 
	private int warehouse_id; 
	private int order_detail_id; 
	private String createby; 
	private Date createdate;
	public int getWarehouse_log_id() {
		return warehouse_log_id;
	}
	public void setWarehouse_log_id(int warehouse_log_id) {
		this.warehouse_log_id = warehouse_log_id;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDatafrom() {
		return datafrom;
	}
	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public int getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(int order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Override
	public String toString() {
		return "WarehouseLog [warehouse_log_id=" + warehouse_log_id + ", num=" + num + ", medicine_id=" + medicine_id
				+ ", price=" + price + ", datafrom=" + datafrom + ", warehouse_id=" + warehouse_id
				+ ", order_detail_id=" + order_detail_id + ", createby=" + createby + ", createdate=" + createdate
				+ "]";
	} 
}
