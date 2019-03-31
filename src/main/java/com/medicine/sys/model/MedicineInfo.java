package com.medicine.sys.model;

import java.util.Date;

public class MedicineInfo {

	private int medicineId; //药品ID
	private String cnName;  //药品名称
	private int num;        //药品数量
	private double inPrice; //药品进货价
	private double pkgPrice; //药品售价
	private String description; //药品描述
	private String isRelease;  //是否发布
	private String func;     //药品功能
	private int contentNum;  //单位数量
	private String contentUnit; //单位名称
	private int operator; //操作人
	private Date operatdate;//操作时间
	public int getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}
	public double getPkgPrice() {
		return pkgPrice;
	}
	public void setPkgPrice(double pkgPrice) {
		this.pkgPrice = pkgPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public int getContentNum() {
		return contentNum;
	}
	public void setContentNum(int contentNum) {
		this.contentNum = contentNum;
	}
	public String getContentUnit() {
		return contentUnit;
	}
	public void setContentUnit(String contentUnit) {
		this.contentUnit = contentUnit;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "MedicineInfo [medicineId=" + medicineId + ", cnName=" + cnName + ", num=" + num + ", inPrice=" + inPrice
				+ ", pkgPrice=" + pkgPrice + ", description=" + description + ", isRelease=" + isRelease + ", func="
				+ func + ", contentNum=" + contentNum + ", contentUnit=" + contentUnit + ", operator=" + operator
				+ ", operatdate=" + operatdate + "]";
	}
}
