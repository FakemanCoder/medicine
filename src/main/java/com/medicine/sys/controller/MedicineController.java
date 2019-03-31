package com.medicine.sys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.medicine.sys.common.RedisUtils;
import com.medicine.sys.common.Utils;
import com.medicine.sys.model.MedicineInfo;
import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.service.MedicineService;

@Controller
@RequestMapping("api/medicine")
public class MedicineController {

	private static final Logger log = LoggerFactory.getLogger(MedicineController.class);
	
	@Autowired
	private MedicineService medicineService;	
	
	@Autowired
	private RedisUtils redis;
	
	//查询药品分页
	@RequestMapping("/medicine_page")
	public String getMedicinePage(HttpServletResponse response,HttpServletRequest request,ModelMap modelMap, PageParam pageParam, String name, String func) {
		log.info("-->MedicineController-->getMedicinePage-->PageParam="+pageParam+",name="+name+",func="+func);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		MedicineInfo medicineInfo = new MedicineInfo();
		medicineInfo.setCnName(name);
		medicineInfo.setFunc(func);
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
			
		}
		List<MedicineInfo> medicineList = medicineService.getMedicinePage(medicineInfo, pageParam);
		log.info("medicineList="+medicineList);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("medicineList", medicineList);
		modelMap.addAttribute("pageParam", pageParam);
		modelMap.addAttribute("name", name);
		modelMap.addAttribute("func", func);
		
		return "/medicine/medicine_list";
	}
	
	//查询药品信息
	@RequestMapping("/getMedicine")
	public String getMedicine(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap,int medicineId) {
		log.info("-->MedicineController-->getMedicine-->medicineId="+medicineId);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		MedicineInfo medicine = medicineService.getMedicine(medicineId);
		
		if (medicine == null) {
			return "medicine/medicine_list";
		}
		modelMap.addAttribute("medicineInfo", medicine);
		
		return "/medicine/medicine_edit";
	}
	
	//跳转到药品入库页面
	@RequestMapping("/inWarehouse")
	public String inWarehouse(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap,int medicineId) {
		log.info("-->MedicineController-->inWarehouse-->medicineId="+medicineId);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		MedicineInfo medicine = medicineService.getMedicine(medicineId);
		
		if (medicine == null) {
			return "medicine/medicine_list";
		}
		modelMap.addAttribute("medicineInfo", medicine);
		
		return "/medicine/in_warehouse";
	}
	
	//编辑库存信息
	@RequestMapping("/editWarehouse")
	public String editWarehouse(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap,int medicineId, int contentNum, double inPrice, String outOrIn) {
		log.info("-->MedicineController-->editWarehouse-->medicineId="+medicineId+",contentNum="+contentNum+",inPrice="+inPrice+",outOrIn="+outOrIn);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		Message msg = medicineService.editWarehouse(medicineId, contentNum, inPrice, outOrIn);
		
		modelMap.addAttribute("msg", msg);
		if (msg == null || msg.getCode() != 0) {
			return "/medicine/in_warehouse";
		}
		PageParam pageParam = new PageParam();
		List<MedicineInfo> medicineList = medicineService.getMedicinePage(new MedicineInfo(), pageParam);
		modelMap.addAttribute("medicineList", medicineList);
		pageParam.setPageNum(1);
		modelMap.addAttribute("pageParam", pageParam);
		
		return "medicine/medicine_list";
	}
	
	//跳转到损耗页面
	@RequestMapping("/toOutWarehouse")
	public String toOutWarehouse(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap,int medicineId) {
		log.info("-->MedicineController-->toOutWarehouse-->medicineId="+medicineId);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		MedicineInfo medicine = medicineService.getMedicine(medicineId);
		
		if (medicine == null) {
			return "medicine/medicine_list";
		}
		modelMap.addAttribute("medicineInfo", medicine);
		
		return "/medicine/out_warehouse";
	}
	
	
	//损耗药品
	@RequestMapping("/outWarehouse")
	@ResponseBody
	public Object outWarehouse(ModelMap modelMap,int medicineId, int num, double inPrice, String outOrIn) {
		log.info("-->MedicineController-->outWarehouse-->medicineId="+medicineId);
		MedicineInfo medicine = medicineService.getMedicine(medicineId);
		Message msg = new Message(0,"操作成功");
		if (medicine.getNum() < num) {
			msg.setCode(21);
			msg.setMsg("损耗数量不能大于库存数量");
		}else {
			msg = medicineService.editWarehouse(medicineId, num, inPrice, outOrIn);
		}
		
		return msg;
	}
	
	//编辑药品信息
	@RequestMapping("/editMedicine")
	public String editMedicine(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap,MedicineInfo medicineInfo) {
		log.info("-->MedicineController-->editMedicine-->medicineInfo="+medicineInfo);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		Message msg = medicineService.editMedicine(medicineInfo);
		modelMap.addAttribute("msg", msg);
		PageParam pageParam = new PageParam();
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
			
		}
		List<MedicineInfo> medicineList = medicineService.getMedicinePage(new MedicineInfo(), pageParam);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("medicineList", medicineList);
		modelMap.addAttribute("pageParam", pageParam);
		
		return "/medicine/medicine_list";
	}
	
	//跳转到添加页面
	@RequestMapping("/toEditPage")
	public String toEditPage(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) {
		log.info("-->MedicineController-->toEditPage-->");
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		modelMap.addAttribute("medicineInfo", new MedicineInfo());
		
		return "/medicine/medicine_edit";
	}
	
	//下架或商家药品
	@RequestMapping("/stopMedicine")
	@ResponseBody
	public Object stopMedicine(HttpServletResponse response,ModelMap modelMap,int medicineId, String isRelease) {
		log.info("-->MedicineController-->stopMedicine-->medicineId="+medicineId+",isRelease="+isRelease);
		
		Message msg = medicineService.stopMedicine(medicineId, isRelease);
		modelMap.addAttribute("msg", msg);
		
		return msg;
	}
	
	private UserInfo getAuth(HttpServletRequest request) {
		String key = Utils.getAuth(request);
		String json = redis.get(key);
		if (json == null) {
			return null;
		}
		UserInfo auth = JSON.parseObject(json, UserInfo.class);
		return auth;
	}
}
