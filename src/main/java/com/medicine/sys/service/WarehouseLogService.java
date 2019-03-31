package com.medicine.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.WarehouseLogInfo;

@Service
public interface WarehouseLogService {
	
	/**
	  * 获取所有仓库记录
	 * @param WarehouseLogInfo
	 * @param pageParam
	 * @return
	 */
	public List<WarehouseLogInfo> getWarehouseLogPage(WarehouseLogInfo warehouseLogInfo, PageParam pageParam);
	
}
