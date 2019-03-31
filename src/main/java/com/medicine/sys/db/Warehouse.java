package com.medicine.sys.db;

/**
 * warehouse实体类
 * 
 * @author 
 *
 */
public class Warehouse {
	private int warehouse_id; 
	private int num; 
	private int medicine_id;
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
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
	@Override
	public String toString() {
		return "Warehouse [warehouse_id=" + warehouse_id + ", num=" + num + ", medicine_id=" + medicine_id + "]";
	} 
}
