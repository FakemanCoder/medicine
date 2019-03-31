package com.medicine.sys.model;

public class WarehouseInfo {
	private int warehouseId; //仓库ID
	private int num;         //仓库数量
	private int medicineId;  //药品ID
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
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
	@Override
	public String toString() {
		return "WarehouseInfo [warehouseId=" + warehouseId + ", num=" + num + ", medicineId=" + medicineId + "]";
	}
}
