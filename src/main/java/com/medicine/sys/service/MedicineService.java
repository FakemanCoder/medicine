package com.medicine.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.MedicineInfo;

@Service
public interface MedicineService {

	/**
	  * 更新或保存药品
	 * @param medicineInfo
	 * @return
	 */
	public Message editMedicine(MedicineInfo medicineInfo);
	
	/**
	  * 获取所有药品
	 * @param medicineInfo
	 * @param pageParam
	 * @return
	 */
	public List<MedicineInfo> getMedicinePage(MedicineInfo medicineInfo, PageParam pageParam);
	
	/**
	  * 获取单个药品信息
	 * @param medicineId
	 * @return
	 */
	public MedicineInfo getMedicine(int medicineId);
	
	/**
	 *  是否发布药品
	 * @param medicineId
	 * @param isRelease
	 * @return
	 */
	public Message stopMedicine(int medicineId, String isRelease);
	
	/**
	 * 进货
	 * @param medicineId
	 * @param contentNum
	 * @param inPrice
	 * @return
	 */
	public Message editWarehouse(int medicineId, int contentNum, double inPrice, String outOrIn);
	
	/**
	 * 获取所有药品信息
	 * @param medicineInfo
	 * @return
	 */
	public List<MedicineInfo> getAllMedicineInfo(MedicineInfo medicineInfo);
}
