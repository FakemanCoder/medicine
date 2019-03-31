package com.medicine.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.OrderSum;
import com.medicine.sys.model.OrderSumInfo;
import com.medicine.sys.model.PageParam;


public interface OrderSumMapper {

	//获取所有总订单分页
	public List<OrderSum> getAll(OrderSumInfo orderSumInfo, PageParam pageParam);
	
	//获取所有总订单数量
	int getAllCount(@Param("orderSumInfo")OrderSumInfo orderSumInfo);

	//通过ID获取总订单
	public List<OrderSum> getOrderSumByIds(@Param("idList")List<Integer> idList);
	 
	//保存总订单
	public int saveOrderSum(@Param("orderSumInfo")OrderSumInfo orderSumInfo);
	 
	//更新总订单
	public void updateOrderSum(@Param("orderSumInfo")OrderSumInfo orderSumInfo);
	 
	//删除总订单
	public void delOrderSum(@Param("OrderSumInfo")OrderSumInfo orderSumInfo);
}
