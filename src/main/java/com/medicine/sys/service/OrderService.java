package com.medicine.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicine.sys.model.Message;
import com.medicine.sys.model.OrderDetailInfo;
import com.medicine.sys.model.OrderSumInfo;
import com.medicine.sys.model.PageParam;

@Service
public interface OrderService {

	/**
	 * 获取订单明细
	 * @return
	 */
	public List<OrderDetailInfo> getOrderDetail(OrderDetailInfo orderDetailInfo);
	
	/**
	 * 获取总订单分页
	 * @param orderSumInfo
	 * @param pageParam
	 * @return
	 */
	public List<OrderSumInfo> getOrderSumPage(OrderSumInfo orderSumInfo, PageParam pageParam);
	
	/**
	 * 删除订单明细
	 * @param orderDetailId
	 * @return
	 */
	public Message delOrderDetail(int orderDetailId);
	
	/**
	 * 根据状态删除订单明细
	 * @param status
	 * @return
	 */
	public Message delOrderDetailByStatus(String status);
	
	/**
	 * 添加订单明细
	 * @param orderDetailInfo
	 * @return
	 */
	public Message addOrderDetail(OrderDetailInfo orderDetailInfo);
	
	/**
	 * 添加总订单
	 * @param orderSumInfo
	 * @return
	 */
	public Message addOrderSum(OrderSumInfo orderSumInfo);
	
	/**
	 * 编辑订单明细
	 * @param orderDetailInfo
	 * @return
	 */
	public Message editOrderDetail(OrderDetailInfo orderDetailInfo);
}
