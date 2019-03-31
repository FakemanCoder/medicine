package com.medicine.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;

@Service
public interface UserService {

	/**
	  * 更新或保存用户
	 * @param userInfo
	 * @return
	 */
	public Message editUser(UserInfo userInfo);
	
	/**
	  * 更新用户密码
	 * @param userInfo
	 * @return
	 */
	public Message editPwd(UserInfo userInfo);
	
	/**
	  * 获取所有用户
	 * @param userInfo
	 * @param pageParam
	 * @return
	 */
	public List<UserInfo> getUserPage(UserInfo userInfo, PageParam pageParam);
	
	/**
	  * 获取单个用户信息
	 * @param userId
	 * @return
	 */
	public UserInfo getUser(int userId);
	
	/**
	  * 用户登录校验
	 * @param username
	 * @param password
	 * @return
	 */
	public UserInfo login(String username, String password);
	
	/**
	 *  是否停止或启动用户登录
	 * @param userId
	 * @param isDel
	 * @return
	 */
	public Message stopUser(int userId, String isDel);
}
