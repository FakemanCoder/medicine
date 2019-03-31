package com.medicine.sys.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.Warehouse;
import com.medicine.sys.model.WarehouseInfo;


public interface WarehouseMapper {

	//获取库存信息
	public List<Warehouse> getWarehouseByMedcineId(@Param("idList")List<Integer> idList);
	 
	//保存库存信息
	public int saveWarehouse(@Param("warehouseInfo")WarehouseInfo warehouseInfo);
	 
	//更新库存信息
	public void updateWarehouse(@Param("warehouseInfo")WarehouseInfo warehouseInfo);
	 

}
