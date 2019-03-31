package com.medicine.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.sys.common.DateUtils;
import com.medicine.sys.db.Medicine;
import com.medicine.sys.db.OrderDetail;
import com.medicine.sys.db.OrderSum;
import com.medicine.sys.db.Warehouse;
import com.medicine.sys.mapper.MedicineMapper;
import com.medicine.sys.mapper.OrderDetailMapper;
import com.medicine.sys.mapper.OrderSumMapper;
import com.medicine.sys.mapper.WarehouseMapper;
import com.medicine.sys.model.MedicineInfo;
import com.medicine.sys.model.Message;
import com.medicine.sys.model.OrderDetailInfo;
import com.medicine.sys.model.OrderSumInfo;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.service.MedicineService;
import com.medicine.sys.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	
	@Autowired
	private OrderSumMapper orderSumMapper;
	
	@Autowired
	private MedicineMapper medicineMapper;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private WarehouseMapper warehouseMapper;
	
	//不分页获取订单明细
	@Override
	public List<OrderDetailInfo> getOrderDetail(OrderDetailInfo orderDetailInfo) {
		log.info("-->OrderServiceImpl-->getOrderDetail-->OrderDetailInfo="+orderDetailInfo);
		List<OrderDetailInfo> orderdetailList = new ArrayList<>();
		//不分页获取订单明细
		List<OrderDetail> allInfo = orderDetailMapper.getAllInfo(orderDetailInfo);
		if (allInfo == null || allInfo.size()<=0) {
			return null;
		}
		//暂存药品ID
		List<Integer> medicineIdList = new ArrayList<>();
		for (OrderDetail orderDetail : allInfo) {
			OrderDetailInfo orderDetailInfo2 = toOrderDetailInfo(orderDetail);
			orderdetailList.add(orderDetailInfo2);
			medicineIdList.add(orderDetail.getMedicine_id());
		}
		//根据以上药品ID查询药品信息
		List<Medicine> medicineList = medicineMapper.getMedicineByIds(medicineIdList);
		if (medicineList == null || medicineList.size()<=0) {
			return null;
		}
		log.info("medicineList="+medicineList);
		//暂存药品信息到medicineMap
		Map<Integer, Medicine> medicineMap = new HashMap<>();
		for (Medicine medicine : medicineList) {
			medicineMap.put(medicine.getMedicine_id(),medicine);
		}
		//组装数据
		for (OrderDetailInfo orderDetail : orderdetailList) {
			Medicine medicine = medicineMap.get(orderDetail.getMedicineId());
			if (medicine != null && medicine.getCn_name() != null) {
				orderDetail.setCnName(medicine.getCn_name());
			}else {
				orderDetail.setCnName("无");
			}
			orderDetail.setPrice(medicine.getPkg_price());
		}
		
		return orderdetailList;
	}

	//分页查询总订单
	@Override
	public List<OrderSumInfo> getOrderSumPage(OrderSumInfo orderSumInfo, PageParam pageParam) {
		log.info("-->OrderServiceImpl-->getOrderSumPage-->OrderSumInfo="+orderSumInfo+",PageParam="+pageParam);
		
		List<OrderSumInfo> orderSumInfoList = new ArrayList<>();
		
		try {
			//为了配合查询，提前将结束时间的天数+1
			if (orderSumInfo!=null && orderSumInfo.getEnddate() != null && !"".equals(orderSumInfo.getEnddate())) {
				String enddate = DateUtils.addDate(orderSumInfo.getEnddate(), 0, 0, 1);
				orderSumInfo.setEnddate(enddate);
			}
			//根据条件查询总订单数量
			int allCount = orderSumMapper.getAllCount(orderSumInfo);
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
			//根据条件查询总订单信息
			List<OrderSum> orderSumList = orderSumMapper.getAll(orderSumInfo, pageParam);
			//组装数据
			for (OrderSum orderSum : orderSumList) {
				OrderSumInfo orderSumInfo2 = toOrderSumInfo(orderSum);
				orderSumInfoList.add(orderSumInfo2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderSumInfoList;
	
	}

	//查询当个订单明细
	@Override
	public Message delOrderDetail(int orderDetailId) {
		log.info("-->OrderServiceImpl-->delOrderDetail-->orderDetailId="+orderDetailId);
		Message msg = new Message(0, "操作成功");
		
		try {
			OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
			orderDetailInfo.setOrderDetailId(orderDetailId);
			orderDetailMapper.delOrderDetail(orderDetailInfo);
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//根据状态删除订单明细
	@Override
	public Message delOrderDetailByStatus(String status) {
		log.info("-->OrderServiceImpl-->delOrderDetailByStatus-->status="+status);
		Message msg = new Message(0, "操作成功");
		
		try {
			OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
			orderDetailInfo.setStatus(status);
			orderDetailMapper.delOrderDetail(orderDetailInfo);
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//保存订单明细
	@Override
	public Message addOrderDetail(OrderDetailInfo orderDetailInfo) {
		log.info("-->OrderServiceImpl-->addOrderDetail-->OrderDetailInfo="+orderDetailInfo);
		Message msg = new Message(0, "操作成功");
		
		try {
			//判断参数是否全
			if (orderDetailInfo == null || orderDetailInfo.getNum() <= 0 || orderDetailInfo.getMedicineId() <= 0) {
				msg.setCode(12);
				msg.setMsg("参数为空或不全");
				return msg;
			}
			//查询添加的数量是否超出库存
			List<Integer> idList = new ArrayList<>();
			idList.add(orderDetailInfo.getMedicineId());
			List<Warehouse> warehouseList = warehouseMapper.getWarehouseByMedcineId(idList);
			if (warehouseList == null ||warehouseList.get(0) == null) {
				msg.setCode(21);
				msg.setMsg("暂无该药品库存");
				return msg;
			}
			if (warehouseList.get(0).getNum() < orderDetailInfo.getNum()) {
				msg.setCode(21);
				msg.setMsg("目前该药品最大库存数量为");
				return msg;
			}
			//查询所有未合成总订单的订单明细信息
			orderDetailInfo.setMedicineId(orderDetailInfo.getMedicineId());
			orderDetailInfo.setStatus("1");
			List<OrderDetail> allInfo = orderDetailMapper.getAllInfo(orderDetailInfo);
			//若已存在订单明细，则增加数量
			if (allInfo != null && allInfo.size() > 0) {
				orderDetailInfo.setStatus(null);
				orderDetailInfo.setNum(orderDetailInfo.getNum()+allInfo.get(0).getNum());
				orderDetailMapper.updateOrderDetail(orderDetailInfo);
			}else {//反之保存订单信息
				orderDetailInfo.setStatus("1");
				orderDetailInfo.setOperatdate(new Date());
				orderDetailInfo.setNum(orderDetailInfo.getNum());
				orderDetailMapper.saveOrderDetail(orderDetailInfo);
			}
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//保存总订单信息
	@Override
	@Transactional
	public Message addOrderSum(OrderSumInfo orderSumInfo) {
		log.info("-->OrderServiceImpl-->addOrderSum-->OrderSumInfo="+orderSumInfo);
		Message msg = new Message(0, "操作成功");
		
		try {
			//保存总订单
			Date date = new Date();
			orderSumInfo.setOperatdate(date);
			orderSumMapper.saveOrderSum(orderSumInfo);
			log.info("-->orderSumMapper-->saveOrderSum-->orderSumInfo="+orderSumInfo);
			//更新订单明细
			OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
			orderDetailInfo.setStatus("2");
			orderDetailInfo.setOrderSumId(orderSumInfo.getOrderSumId());
			orderDetailInfo.setOperatdate(date);
			orderDetailMapper.updateOrderDetailByStatus(orderDetailInfo);
			//根据总订单ID查询所有的订单明细信息
			List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailByOrderSumId(orderSumInfo.getOrderSumId());
			//更新对应药品的库存
			for (OrderDetail orderDetail : orderDetailList) {
				MedicineInfo medicine = medicineService.getMedicine(orderDetail.getMedicine_id());
				Message editMsg = medicineService.editWarehouse(orderDetail.getMedicine_id(), orderDetail.getNum(), medicine.getPkgPrice(), "fafang");
				if (editMsg == null || editMsg.getCode() != 0) {
					throw new RuntimeException();
				}
			}
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//编辑订单明细
	@Override
	@Transactional
	public Message editOrderDetail(OrderDetailInfo orderDetailInfo) {
		log.info("-->OrderServiceImpl-->editOrderDetail-->OrderDetailInfo="+orderDetailInfo);
		Message msg = new Message(0, "操作成功");
		
		try {
			//查询该订单信息
			List<Integer> idList = new ArrayList<>();
			idList.add(orderDetailInfo.getOrderDetailId());
			List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailByIds(idList);
			if (orderDetailList == null) {
				msg.setCode(50);
				msg.setMsg("数据错误");
				return msg;
			}
			//查询药品信息
			List<Integer> medicineIdList = new ArrayList<>();
			medicineIdList.add(orderDetailList.get(0).getMedicine_id());
			List<Medicine> medicineList = medicineMapper.getMedicineByIds(medicineIdList);
			if (medicineList == null) {
				msg.setCode(50);
				msg.setMsg("数据错误");
				return msg;
			}
			//更新订单明细的状态
			orderDetailInfo.setStatus("3");
			orderDetailInfo.setOrderDetailId(orderDetailInfo.getOrderDetailId());
			orderDetailMapper.updateOrderDetail(orderDetailInfo);
			//更新总订单金额
			OrderSumInfo orderSumInfo = new OrderSumInfo();
			orderSumInfo.setPrice(medicineList.get(0).getPkg_price());
			orderSumInfo.setOrderSumId(orderDetailList.get(0).getOrder_sum_id());
			orderSumMapper.updateOrderSum(orderSumInfo);
			//增加对应药品的库存数量
			Message msg2 = medicineService.editWarehouse(orderDetailList.get(0).getMedicine_id(), orderDetailList.get(0).getNum(), medicineList.get(0).getPkg_price(), "tuihuo");
			if (msg2==null || msg2.getCode() != 0) {
				msg.setCode(50);
				msg.setMsg("服务器内部错误");
				throw new RuntimeException();
			}
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}
	
	private OrderDetailInfo toOrderDetailInfo(OrderDetail orderDetail) {
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		orderDetailInfo.setMedicineId(orderDetail.getMedicine_id());
		orderDetailInfo.setNum(orderDetail.getNum());
		orderDetailInfo.setOrderDetailId(orderDetail.getOrder_detail_id());
		orderDetailInfo.setOrderSumId(orderDetail.getOrder_sum_id());
		orderDetailInfo.setStatus(orderDetail.getStatus());
		if ("1".equals(orderDetail.getStatus())) {
			orderDetailInfo.setStatusName("未合成总订单");
		}else if("2".equals(orderDetail.getStatus())) {
			orderDetailInfo.setStatusName("正常");
		}else if("3".equals(orderDetail.getStatus())) {
			orderDetailInfo.setStatusName("已退款");
		}
		orderDetailInfo.setWarehouseUserId(orderDetail.getWarehouse_user_id());
		return orderDetailInfo;
	}
	
	private OrderSumInfo toOrderSumInfo(OrderSum orderSum) {
		OrderSumInfo orderSumInfo = new OrderSumInfo();
		orderSumInfo.setOperatdate(orderSum.getCreatedate());
		orderSumInfo.setOrderSumId(orderSum.getOrder_sum_id());
		orderSumInfo.setPrice(orderSum.getPrice());
		return orderSumInfo;
	}

}
