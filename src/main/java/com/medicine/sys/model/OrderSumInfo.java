package com.medicine.sys.model;

import java.util.Date;

public class OrderSumInfo {
	private int orderSumId; //总订单ID
	private double price;   //总订单金额
	private int operator;   //操作人
	private Date operatdate;//操作时间
	private String startdate;//开始时间
	private String enddate;  //结束时间
	public int getOrderSumId() {
		return orderSumId;
	}
	public void setOrderSumId(int orderSumId) {
		this.orderSumId = orderSumId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	@Override
	public String toString() {
		return "OrderSumInfo [orderSumId=" + orderSumId + ", price=" + price + ", operator=" + operator
				+ ", operatdate=" + operatdate + ", startdate=" + startdate + ", enddate=" + enddate + "]";
	}
}
