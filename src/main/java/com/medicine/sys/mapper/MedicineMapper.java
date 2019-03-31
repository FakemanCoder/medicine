package com.medicine.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.Medicine;
import com.medicine.sys.model.MedicineInfo;
import com.medicine.sys.model.PageParam;


public interface MedicineMapper {

	//获取所有药品分页
	public List<Medicine> getAll(MedicineInfo medicineInfo, PageParam pageParam);
	
	//获取所有药品数量
	public int getAllCount(@Param("medicineInfo")MedicineInfo medicineInfo);
	
	//获取所有药品不分页
	public List<Medicine> getAllMedicine(@Param("medicineInfo")MedicineInfo medicineInfo);

	//通过ID获取药品信息
	public List<Medicine> getMedicineByIds(@Param("idList")List<Integer> idList);
	
	//获取药品信息
	public Medicine getMedicine(int medicineId);
	 
	//保存药品信息
	public void saveMedicine(@Param("medicineInfo")MedicineInfo medicineInfo);
	 
	//更新药品信息
	public void updateMedicine(@Param("medicineInfo")MedicineInfo medicineInfo);
	 
	//停止发售药品
	public void stopMedicine(int medicineId, String isRelease);
}
