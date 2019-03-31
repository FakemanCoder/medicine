package com.medicine.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.OrderDetail;
import com.medicine.sys.model.OrderDetailInfo;
import com.medicine.sys.model.PageParam;


public interface OrderDetailMapper {

	//获取所有订单明细分页
	public List<OrderDetail> getAll(OrderDetailInfo orderDetailInfo, PageParam pageParam);
	
	//获取所有订单明细不分页
	public List<OrderDetail> getAllInfo(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);
	
	//获取所有订单明细数量
	int getAllCount(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);

	//通过ID获取订单明细
	public List<OrderDetail> getOrderDetailByIds(@Param("idList")List<Integer> idList);
	
	//通过总订单号获取订单明细
	public List<OrderDetail> getOrderDetailByOrderSumId(int orderSumId);
	 
	//保存订单明细
	public void saveOrderDetail(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);
	 
	//更新订单明细
	public void updateOrderDetail(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);
	
	//生成总订单更新订单明细
	public void updateOrderDetailByStatus(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);
	 
	//删除订单明细
	public void delOrderDetail(@Param("orderDetailInfo")OrderDetailInfo orderDetailInfo);
}
