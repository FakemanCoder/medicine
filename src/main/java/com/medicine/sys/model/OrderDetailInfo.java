package com.medicine.sys.model;

import java.util.Date;

public class OrderDetailInfo {
	private String cnName;     //药品名称
	private int orderDetailId; //订单明细ID
	private int orderSumId;    //总订单ID
	private String status; //1:未合成订单，2:正常，3:退款
	private String statusName; //1:未合成订单，2:正常，3:退款
	private int num;           //药品数量
	private double price;      //药品价格
	private int medicineId;    //药品ID
	private int warehouseUserId; //仓库操作人
	private int operator;      //操作人
	private Date operatdate;   //操作时间
	public int getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public int getOrderSumId() {
		return orderSumId;
	}
	public void setOrderSumId(int orderSumId) {
		this.orderSumId = orderSumId;
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
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public int getWarehouseUserId() {
		return warehouseUserId;
	}
	public void setWarehouseUserId(int warehouseUserId) {
		this.warehouseUserId = warehouseUserId;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public Date getOperatdate() {
		return operatdate;
	}
	public void setOperatdate(Date operatdate) {
		this.operatdate = operatdate;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Override
	public String toString() {
		return "OrderDetailInfo [cnName=" + cnName + ", orderDetailId=" + orderDetailId + ", orderSumId=" + orderSumId
				+ ", status=" + status + ", statusName=" + statusName + ", num=" + num + ", price=" + price
				+ ", medicineId=" + medicineId + ", warehouseUserId=" + warehouseUserId + ", operator=" + operator
				+ ", operatdate=" + operatdate + "]";
	}
}
