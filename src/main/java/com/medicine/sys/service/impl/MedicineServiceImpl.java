package com.medicine.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.sys.db.Medicine;
import com.medicine.sys.db.Warehouse;
import com.medicine.sys.mapper.MedicineMapper;
import com.medicine.sys.mapper.WarehouseLogMapper;
import com.medicine.sys.mapper.WarehouseMapper;
import com.medicine.sys.model.MedicineInfo;
import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.WarehouseInfo;
import com.medicine.sys.model.WarehouseLogInfo;
import com.medicine.sys.service.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService {

	private static final Logger log = LoggerFactory.getLogger(MedicineServiceImpl.class);
	
	@Autowired
	private MedicineMapper medicineMapper;
	
	@Autowired
	private WarehouseMapper warehouseMapper;
	
	@Autowired
	private WarehouseLogMapper warehouseLogMapper;

	//编辑药品信息
	@Override
	public Message editMedicine(MedicineInfo medicineInfo) {
		log.info("-->MedicineServiceImpl-->editMedicine-->MedicineInfo="+medicineInfo);
		Message msg = new Message(0,"查询成功");
		if (medicineInfo == null) {
			msg.setCode(21);
			msg.setMsg("参数为空或不全");
			return msg;
		}
		try {
			//若传入药品ID，则更新药品信息
			if (medicineInfo.getMedicineId() > 0) {
				medicineMapper.updateMedicine(medicineInfo);
			}else {//反之，保存新药品信息
				medicineMapper.saveMedicine(medicineInfo);
			}
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//查询药品分页
	@Override
	public List<MedicineInfo> getMedicinePage(MedicineInfo medicineInfo, PageParam pageParam) {
		log.info("-->MedicineServiceImpl-->getMedicinePage-->MedicineInfo="+medicineInfo+",PageParam="+pageParam);
		
		List<MedicineInfo> medicineInfoList = new ArrayList<>();
		
		try {
			//传入药品名称，功能条件，提前加入模糊查询的%%
			if (medicineInfo != null && medicineInfo.getCnName() != null && !"".equals(medicineInfo.getCnName())) {
				medicineInfo.setCnName("%"+medicineInfo.getCnName()+"%");
			}
			if (medicineInfo != null && medicineInfo.getFunc() != null && !"".equals(medicineInfo.getFunc())) {
				medicineInfo.setFunc("%"+medicineInfo.getFunc()+"%");
			}
			//根据条件查询药品数量
			int allCount = medicineMapper.getAllCount(medicineInfo);
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
			//根据条件查询药品信息分页
			List<Medicine> medicineList = medicineMapper.getAll(medicineInfo, pageParam);
			//暂存药品ID，方面查询相应的库存数量
			List<Integer> medicineIdList = new ArrayList<>();
			for (Medicine medicine : medicineList) {
				MedicineInfo medicineInfo2 = toMedicineInfo(medicine);
				medicineIdList.add(medicine.getMedicine_id());
				medicineInfoList.add(medicineInfo2);
			}
			//查询对应药品库存数量
			List<Warehouse> warehouseList = warehouseMapper.getWarehouseByMedcineId(medicineIdList);
			//每个药品信息暂存warehouseMap
			Map<Integer, Integer> warehouseMap = new HashMap<>();
			for (Warehouse warehouseInfo : warehouseList) {
				warehouseMap.put(warehouseInfo.getMedicine_id(), warehouseInfo.getNum());
			}
			//循环设置药品库存数量
			for (MedicineInfo medicine : medicineInfoList) {
				Integer num = warehouseMap.get(medicine.getMedicineId());
				if (num == null) {
					continue;
				}
				medicine.setNum(num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicineInfoList;
	}

	//查询药品信息
	@Override
	public MedicineInfo getMedicine(int medicineId) {
		log.info("-->MedicineServiceImpl-->getMedicine-->medicineId="+medicineId);
		if (medicineId <= 0) {
			log.info("medicineId参数不可法");
			return null;
		}
		MedicineInfo medicineInfo = null;
		try {
			Medicine medicine = medicineMapper.getMedicine(medicineId);
			if (medicine != null) {
				medicineInfo = toMedicineInfo(medicine);
			}
			List<Integer> idList = new ArrayList<>();
			idList.add(medicineId);
			List<Warehouse> warehouseList= warehouseMapper.getWarehouseByMedcineId(idList);
			if (warehouseList != null && warehouseList.size() > 0 && warehouseList.get(0) != null) {
				medicineInfo.setNum(warehouseList.get(0).getNum());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicineInfo;
	}
	
	//商家或下架药品
	@Override
	public Message stopMedicine(int medicineId, String isRelease) {
		log.info("-->MedicineServiceImpl-->stopMedicine-->medicineId="+medicineId+",isRelease="+isRelease);
		Message msg = new Message(0, "操作成功");
		try {
			
			medicineMapper.stopMedicine(medicineId, isRelease);
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}
	
	private MedicineInfo toMedicineInfo(Medicine medicine) {
		MedicineInfo medicineInfo = new MedicineInfo();
		medicineInfo.setCnName(medicine.getCn_name());
		medicineInfo.setContentNum(medicine.getContent_num());
		medicineInfo.setContentUnit(medicine.getContent_unit());
		medicineInfo.setDescription(medicine.getDescription());
		if (medicine.getDescription() == null || "".equals(medicine.getDescription())) {
			medicineInfo.setDescription("无");
		}
		medicineInfo.setFunc(medicine.getFunc());
		medicineInfo.setInPrice(medicine.getIn_price());
		medicineInfo.setIsRelease(medicine.getIs_release());
		medicineInfo.setMedicineId(medicine.getMedicine_id());
		medicineInfo.setPkgPrice(medicine.getPkg_price());
		return medicineInfo;
	}

	//编辑库存记录信息
	@Override
	public Message editWarehouse(int medicineId, int contentNum, double inPrice, String outOrIn) {
		log.info("-->MedicineServiceImpl-->editWarehouse-->medicineId="+medicineId+",contentNum="+contentNum+",inPrice="+inPrice);
		Message msg = new Message(0,"操作成功");
		try {
			//查询药品库存信息
			List<Integer> idList = new ArrayList<>();
			idList.add(medicineId);
			List<Warehouse> warehouseList = warehouseMapper.getWarehouseByMedcineId(idList);
			WarehouseInfo warehouseInfo = null;
			int warehouseId = 0;
			if ("fafang".equals(outOrIn)||"sunhao".equals(outOrIn)) {
				contentNum = contentNum*-1;
				inPrice = inPrice*-1;
			}
			//若未入库，则插入库存总表
			if (warehouseList == null || warehouseList.size() == 0) {
				warehouseInfo = new WarehouseInfo();
				warehouseInfo.setNum(contentNum);
				warehouseInfo.setMedicineId(medicineId);
				warehouseMapper.saveWarehouse(warehouseInfo);
				warehouseId = warehouseInfo.getWarehouseId();
			}else {//若已经入库，则更新库存数量
				warehouseId = warehouseList.get(0).getMedicine_id();
				warehouseInfo = new WarehouseInfo();
				warehouseInfo.setMedicineId(medicineId);
				warehouseInfo.setNum(warehouseList.get(0).getNum()+contentNum);
				warehouseMapper.updateWarehouse(warehouseInfo);
			}
			//保存库存明细
			WarehouseLogInfo warehouseLogInfo = new WarehouseLogInfo();
			warehouseLogInfo.setDatafrom(outOrIn);
			warehouseLogInfo.setMedicineId(medicineId);
			warehouseLogInfo.setNum(contentNum);
			warehouseLogInfo.setPrice(inPrice*contentNum);
			warehouseLogInfo.setWarehouseId(warehouseId);
			warehouseLogInfo.setCreatedate(new Date());
			warehouseLogMapper.saveWarehouseLog(warehouseLogInfo);
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//不分页查询所有药品信息
	@Override
	public List<MedicineInfo> getAllMedicineInfo(MedicineInfo medicineInfo) {
		log.info("-->MedicineServiceImpl-->getAllMedicineInfo-->MedicineInfo="+medicineInfo);
		List<MedicineInfo> medicineInfoList = new ArrayList<>();
		
		List<Medicine> medicineList = medicineMapper.getAllMedicine(medicineInfo);
		for (Medicine medicine : medicineList) {
			MedicineInfo medicineInfo2 = toMedicineInfo(medicine);
			medicineInfoList.add(medicineInfo2);
		}
		
		return medicineInfoList;
	}
	
}
