package com.medicine.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.sys.db.User;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;


public interface UserMapper {

	//条件查询所有用户
	public List<User> getAll(UserInfo userInfo, PageParam pageParam);
	
	//条件查询用户数量
	int getAllCount(@Param("userInfo")UserInfo userInfo);

	//登录校验
	public User login(String username, String password);
	
	//获取单个用户
	public User getUser(int userId);
	
	//保存用户
	public void saveUser(@Param("userInfo")UserInfo userInfo);
	
	//更新用户
	public void updateUser(@Param("userInfo")UserInfo userInfo);
	
	//停用或启用用户
	public void stopUser(int userId, String isDel);
}
