package com.medicine.sys.model;

import java.util.Date;

public class WarehouseLogInfo {
	private int warehouseLogId; //仓库明细ID
	private String name;        //药品名称
	private int num;            //该明细药品数量
	private int medicineId;     //药品ID
	private double price;       //药品价格
	private String datafrom;    //来源
	private String datafromName;    //来源
	private int warehouseId;    //仓库ID
	private int orderDetailId;  //订单明细ID
	private String createby;    //创建人ID
	private Date createdate;    //创建时间
	private String startdate;   //开始时间
	private String enddate;     //结束时间
	public int getWarehouseLogId() {
		return warehouseLogId;
	}
	public void setWarehouseLogId(int warehouseLogId) {
		this.warehouseLogId = warehouseLogId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
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
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public int getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
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
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatafromName() {
		return datafromName;
	}
	public void setDatafromName(String datafromName) {
		this.datafromName = datafromName;
	}
	@Override
	public String toString() {
		return "WarehouseLogInfo [warehouseLogId=" + warehouseLogId + ", name=" + name + ", num=" + num
				+ ", medicineId=" + medicineId + ", price=" + price + ", datafrom=" + datafrom + ", datafromName="
				+ datafromName + ", warehouseId=" + warehouseId + ", orderDetailId=" + orderDetailId + ", createby="
				+ createby + ", createdate=" + createdate + ", startdate=" + startdate + ", enddate=" + enddate + "]";
	}
}
