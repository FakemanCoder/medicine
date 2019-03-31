package com.medicine.sys.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.WarehouseLog;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.WarehouseLogInfo;


public interface WarehouseLogMapper {
	 
	//获取所有库存记录分页
	public List<WarehouseLog> getAll(WarehouseLogInfo warehouseLogInfo, PageParam pageParam);
	
	//获取所有库存记录数量
	int getAllCount(@Param("warehouseLogInfo")WarehouseLogInfo warehouseLogInfo);
	
	//保存库存日志信息
	public void saveWarehouseLog(@Param("warehouseLogInfo")WarehouseLogInfo warehouseLogInfo);

}
