package com.medicine.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.sys.common.DateUtils;
import com.medicine.sys.db.Medicine;
import com.medicine.sys.db.WarehouseLog;
import com.medicine.sys.mapper.MedicineMapper;
import com.medicine.sys.mapper.WarehouseLogMapper;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.WarehouseLogInfo;
import com.medicine.sys.service.WarehouseLogService;

@Service
public class WarehouseLogServiceImpl implements WarehouseLogService {

	private static final Logger log = LoggerFactory.getLogger(WarehouseLogServiceImpl.class);
	
	@Autowired
	private WarehouseLogMapper warehouseLogMapper;
	
	@Autowired
	private MedicineMapper medicineMapper;

	//查询库存记录分页
	@Override
	public List<WarehouseLogInfo> getWarehouseLogPage(WarehouseLogInfo warehouseLogInfo, PageParam pageParam) {
		log.info("-->MedicineServiceImpl-->getWarehouseLogPage-->WarehouseLogInfo="+warehouseLogInfo+",PageParam="+pageParam);
		
		List<WarehouseLogInfo> warehouseLogInfoList = new ArrayList<>();
		
		try {
			//为了配合查询，提前将结束时间的天数+1
			if (warehouseLogInfo!=null && warehouseLogInfo.getEnddate() != null && !"".equals(warehouseLogInfo.getEnddate())) {
				String enddate = DateUtils.addDate(warehouseLogInfo.getEnddate(), 0, 0, 1);
				warehouseLogInfo.setEnddate(enddate);
			}
			//根据条件查询库存记录数量
			int allCount = warehouseLogMapper.getAllCount(warehouseLogInfo);
			if (allCount <= 0) {
				log.info("暂无数据！");
				return null;
			}
			log.info("allCount="+allCount);
			if (pageParam == null) {
				pageParam = new PageParam();
			}
			
			if (pageParam.getPageNum() == 0) {
				pageParam.setPageNum(1);
			}
			
			if (pageParam.getPageSize() == 0) {
				pageParam.setPageSize(10);
			}
			
			int maxPage = (int) Math.ceil((double)allCount/pageParam.getPageSize());
			pageParam.setMaxPage(maxPage);
			
			if (maxPage < pageParam.getPageNum() && maxPage > 1) {
				pageParam.setPageNum(maxPage);
			}
			
			pageParam.setPageNum((pageParam.getPageNum()-1)*pageParam.getPageSize());
			//根据条件查询所有库存信息
			List<WarehouseLog> warehouseLogList = warehouseLogMapper.getAll(warehouseLogInfo, pageParam);
			//暂存药品ID
			List<Integer> medicineIdList = new ArrayList<>();
			for (WarehouseLog warehouseLog : warehouseLogList) {
				WarehouseLogInfo warehouseLogInfo2 = toWarehouseLogInfo(warehouseLog);
				medicineIdList.add(warehouseLog.getMedicine_id());
				warehouseLogInfoList.add(warehouseLogInfo2);
			}
			//根据以上药品ID查询药品信息
			List<Medicine> medicineList = medicineMapper.getMedicineByIds(medicineIdList);
			//暂存药品信息到medicineMap
			Map<Integer, String> medicineMap = new HashMap<>();
			for (Medicine medicine : medicineList) {
				medicineMap.put(medicine.getMedicine_id(), medicine.getCn_name());
			}
			//组装数据
			for (WarehouseLogInfo warehouseLogInfo2 : warehouseLogInfoList){
				String name = medicineMap.get(warehouseLogInfo2.getMedicineId());
				if (name == null) {
					warehouseLogInfo2.setName("无");
				}else {
					warehouseLogInfo2.setName(name);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return warehouseLogInfoList;
	}
	
	public WarehouseLogInfo toWarehouseLogInfo(WarehouseLog warehouseLog) {
		WarehouseLogInfo warehouseLogInfo = new WarehouseLogInfo();
		
		warehouseLogInfo.setDatafrom(warehouseLog.getDatafrom());
		if ("jinhuo".equals(warehouseLog.getDatafrom())) {
			warehouseLogInfo.setDatafromName("进货入库");
		}else if("tuihuo".equals(warehouseLog.getDatafrom())) {
			warehouseLogInfo.setDatafromName("药品退货");
		}else if("sunhao".equals(warehouseLog.getDatafrom())) {
			warehouseLogInfo.setDatafromName("药品损耗");
		}else if("fafang".equals(warehouseLog.getDatafrom())){
			warehouseLogInfo.setDatafromName("药品发放");
		}else {
			warehouseLogInfo.setDatafromName("其他");
		}
		warehouseLogInfo.setCreatedate(warehouseLog.getCreatedate());
		warehouseLogInfo.setMedicineId(warehouseLog.getMedicine_id());
		warehouseLogInfo.setNum(warehouseLog.getNum());
		warehouseLogInfo.setPrice(warehouseLog.getPrice());
		warehouseLogInfo.setWarehouseLogId(warehouseLog.getWarehouse_log_id());
		
		return warehouseLogInfo;
	}
}
