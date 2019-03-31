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

import com.alibaba.fastjson.JSON;
import com.medicine.sys.common.RedisUtils;
import com.medicine.sys.common.Utils;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.model.WarehouseLogInfo;
import com.medicine.sys.service.WarehouseLogService;

@Controller
@RequestMapping("api/warehouse")
public class WarehouseController {

	private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);
	
	@Autowired
	private WarehouseLogService warehouseLogService;
	
	@Autowired
	private RedisUtils redis;
	
	//库存明细分页
	@RequestMapping("/warehouse_page")
	public String getWarehousePage(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap, PageParam pageParam, String startdate, String enddate, String datafrom) {
		log.info("-->WarehouseController-->getWarehousePage-->PageParam="+pageParam+",startdate="+startdate+",enddate="+enddate+",datafrom="+datafrom);
		
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		WarehouseLogInfo warehouseLogInfo = new WarehouseLogInfo();
		warehouseLogInfo.setDatafrom(datafrom);
		warehouseLogInfo.setStartdate(startdate);
		warehouseLogInfo.setEnddate(enddate);
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
		}
		//查询库存明细分页
		List<WarehouseLogInfo> warehouseLogList = warehouseLogService.getWarehouseLogPage(warehouseLogInfo, pageParam);
		log.info("warehouseLogList="+warehouseLogList);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("warehouseLogList", warehouseLogList);
		modelMap.addAttribute("pageParam", pageParam);
		modelMap.addAttribute("startdate", startdate);
		modelMap.addAttribute("enddate", enddate);
		modelMap.addAttribute("datafrom", datafrom);
		
		return "/warehouse/warehouse_list";
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
