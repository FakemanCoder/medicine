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
import com.medicine.sys.model.OrderDetailInfo;
import com.medicine.sys.model.OrderSumInfo;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.service.MedicineService;
import com.medicine.sys.service.OrderService;

@Controller
@RequestMapping("api/order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private RedisUtils redis;
	
	//查询总订单分页
	@RequestMapping("/order_sum_page")
	public String getOrderSumPage(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap, PageParam pageParam, String startdate, String enddate) {
		log.info("-->OrderController-->getOrderSumPage-->PageParam="+pageParam);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		
		OrderSumInfo orderSumInfo = new OrderSumInfo();
		orderSumInfo.setStartdate(startdate);
		orderSumInfo.setEnddate(enddate);
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
			
		}
		//查询总订单分页
		List<OrderSumInfo> orderSumList = orderService.getOrderSumPage(orderSumInfo, pageParam);
		log.info("orderSumList="+orderSumList);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("orderSumList", orderSumList);
		modelMap.addAttribute("pageParam", pageParam);
		modelMap.addAttribute("startdate", startdate);
		modelMap.addAttribute("enddate", enddate);
		
		return "/order/order_list";
	}
	
	//查询添加订单前的数据
	@RequestMapping("/get_add_order")
	public String getAddOrder(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		log.info("-->OrderController-->getAddOrder-->");
		
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("auth="+auth);
		modelMap.addAttribute("auth", auth);
		
		//查询未合成总订单的订单明细
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setStatus("1");
		List<OrderDetailInfo> orderDetailList = orderService.getOrderDetail(orderDetailInfo);
		modelMap.addAttribute("orderDetailList", orderDetailList);
		//通过订单明细计算，订单总金额，药品总数量
		double price = 0;
		int num = 0;
		if (orderDetailList != null) {
			for (OrderDetailInfo orderDetail : orderDetailList) {
				price += orderDetail.getPrice()*orderDetail.getNum();
				num += orderDetail.getNum();
			}
		}
		modelMap.addAttribute("price", price);
		modelMap.addAttribute("num", num);
		//不分页查询所有药品信息
		MedicineInfo medicineInfo = new MedicineInfo();
		medicineInfo.setIsRelease("T");
		List<MedicineInfo> medicineInfoList = medicineService.getAllMedicineInfo(medicineInfo );
		modelMap.addAttribute("medicineInfoList", medicineInfoList);
		
		return "/order/add_order";
	}
	
	//添加订单明细
	@RequestMapping("/add_order_detail")
	@ResponseBody
	public Object addOrderDetail(int medicineId, int num) {
		log.info("-->OrderController-->addOrderDetail-->medicineId="+medicineId+",num="+num);
		
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setMedicineId(medicineId);
		orderDetailInfo.setNum(num);
		Message msg = orderService.addOrderDetail(orderDetailInfo);
		
		return msg;
	}
	//清空未合成总订单的订单明细
	@RequestMapping("/del_all_order_detail")
	@ResponseBody
	public Object delAllOrderDetail() {
		log.info("-->OrderController-->delAllOrderDetail-->");
		
		Message msg = orderService.delOrderDetailByStatus("1");
		
		return msg;
	}
	
	//删除单条订单明细
	@RequestMapping("/del_order_detail")
	@ResponseBody
	public Object delOrderDetail(int orderDetailId) {
		log.info("-->OrderController-->delOrderDetail-->orderDetailId="+orderDetailId);
		
		Message msg = orderService.delOrderDetail(orderDetailId);
		
		return msg;
	}
	
	//添加总订单明细
	@RequestMapping("/add_order")
	@ResponseBody
	public Object addOrder(double price) {
		log.info("-->OrderController-->addOrder-->");
		OrderSumInfo orderSumInfo = new OrderSumInfo();
		orderSumInfo.setPrice(price);
		Message msg = orderService.addOrderSum(orderSumInfo);
		
		return msg;
	}
	
	//获取订单明细
	@RequestMapping("/get_order_detail")
	public String getOrderDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, int orderSumId) {
		log.info("-->OrderController-->getOrderDetail-->orderSumId="+orderSumId);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setOrderSumId(orderSumId);
		List<OrderDetailInfo> orderDetailList = orderService.getOrderDetail(orderDetailInfo);
		modelMap.addAttribute("orderDetailList", orderDetailList);
		modelMap.addAttribute("orderSumId", orderSumId);
		
		return "/order/order_detail_list";
	}
	
	//订单明细退款
	@RequestMapping("/return_order_detail")
	@ResponseBody
	public Object returnOrderDetail(int orderDetailId) {
		log.info("-->OrderController-->returnOrderDetail-->orderDetailId="+orderDetailId);
		
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setOrderDetailId(orderDetailId);
		Message msg = orderService.editOrderDetail(orderDetailInfo);
		
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
