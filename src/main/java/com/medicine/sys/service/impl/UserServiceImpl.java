package com.medicine.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.sys.common.Utils;
import com.medicine.sys.db.User;
import com.medicine.sys.mapper.UserMapper;
import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper usermapper;

	//编辑用户信息
	@Override
	public Message editUser(UserInfo userInfo) {
		log.info("-->UserServiceImpl-->editUser-->UserInfo="+userInfo);
		Message msg = new Message(0,"查询成功");
		if (userInfo == null) {
			msg.setCode(21);
			msg.setMsg("参数为空或不全");
			return msg;
		}
		try {
			if (userInfo.getPassword() != null) {
				userInfo.setPassword(Utils.getMD5(userInfo.getPassword()));
			}
			//若传入用户ID，则更新用户信息
			if (userInfo.getUserId() > 0) {
				usermapper.updateUser(userInfo);
			}else {//反之保存用户信息
				usermapper.saveUser(userInfo);
			}
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}

	//查询用户分页
	@Override
	public List<UserInfo> getUserPage(UserInfo userInfo, PageParam pageParam) {
		log.info("-->UserServiceImpl-->getUserPage-->UserInfo="+userInfo+",PageParam="+pageParam);
		
		List<UserInfo> userInfoList = new ArrayList<>();
		
		try {
			//有学生姓名条件，提前设置姓名的模糊查询
			if (userInfo != null && userInfo.getRealname() != null && !"".equals(userInfo.getRealname())) {
				userInfo.setRealname("%"+userInfo.getRealname()+"%");
			}
			//根据条件查询所有用户的数量
			int allCount = usermapper.getAllCount(userInfo);
			if (allCount <= 0) {
				log.info("暂无数据！");
				return null;
			}
			log.info("allCount="+allCount);
			//传入页码信息为空，则new出来
			if (pageParam == null) {
				pageParam = new PageParam();
			}
			//当前页码为0，则设置第一页
			if (pageParam.getPageNum() == 0) {
				pageParam.setPageNum(1);
			}
			//每页行数为0，则默认每页显示10行
			if (pageParam.getPageSize() == 0) {
				pageParam.setPageSize(10);
			}
			//计算最大页码
			int maxPage = (int) Math.ceil((double)allCount/pageParam.getPageSize());
			pageParam.setMaxPage(maxPage);
			//当前页码大于最大页码，则将最大页码设置为当前页码
			if (maxPage < pageParam.getPageNum() && maxPage > 1) {
				pageParam.setPageNum(maxPage);
			}
			
			pageParam.setPageNum((pageParam.getPageNum()-1)*pageParam.getPageSize());
			//根据条件查询所有用户信息
			List<User> userList = usermapper.getAll(userInfo, pageParam);
			//组装用户信息
			for (User user : userList) {
				UserInfo userInfo2 = toUserInfo(user);
				userInfoList.add(userInfo2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoList;
	}

	//查询单个用户信息
	@Override
	public UserInfo getUser(int userId) {
		log.info("-->UserServiceImpl-->getUser-->userId="+userId);
		if (userId <= 0) {
			log.info("userId参数不可法");
			return null;
		}
		UserInfo userInfo = null;
		try {
			//查询单个个人信息
			User user = usermapper.getUser(userId);
			if (user != null) {
				userInfo = toUserInfo(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	//校验用户账号密码
	@Override
	public UserInfo login(String username, String password) {
		log.info("-->UserServiceImpl-->login-->username="+username+",password="+password);
		if (username == null || password == null) {
			log.info("参数为空或不全");
			return null;
		}
		UserInfo userInfo = null;
		try {
			//将用户输入的密码加密对比
			String pwd = Utils.getMD5(password);
			log.info("pwd="+pwd);
			User user = usermapper.login(username, pwd);
			if (user != null) {
				userInfo = toUserInfo(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	//停用或重启用户
	@Override
	public Message stopUser(int userId, String isDel) {
		log.info("-->UserServiceImpl-->login-->userId="+userId+",isDel="+isDel);
		Message msg = new Message(0, "操作成功");
		try {
			
			usermapper.stopUser(userId, isDel);
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}
	
	private UserInfo toUserInfo(User user) {
		UserInfo userInfo = new UserInfo();
		userInfo.setAddress(user.getAddress());
		userInfo.setEmail(user.getEmail());
		userInfo.setMobile(user.getMobile());
		userInfo.setIsDel(user.getIs_del());
		userInfo.setPassword(user.getPassword());
		userInfo.setOperator(user.getCreateby());
		userInfo.setRealname(user.getRealname());
		userInfo.setRole(user.getRole());
		if ("admin".equals(user.getRole())) {
			userInfo.setRoleName("管理员");
		}else {
			userInfo.setRoleName("普通员工");
		}
		userInfo.setUserId(user.getUser_id());
		userInfo.setUsername(user.getUsername());
		return userInfo;
	}

	@Override
	public Message editPwd(UserInfo userInfo) {
		log.info("-->UserServiceImpl-->editPwd-->UserInfo="+userInfo);
		Message msg = new Message(0,"操作成功");
		if (userInfo == null || userInfo.getUserId() <= 0 || userInfo.getOldPwd() == null || userInfo.getPassword() == null) {
			msg.setCode(21);
			msg.setMsg("参数为空或不全");
			return msg;
		}
		try {
			UserInfo user = getUser(userInfo.getUserId());
			if (user == null) {
				msg.setCode(50);
				msg.setMsg("查无此人");
				return msg;
			}
			UserInfo login = login(user.getMobile(), userInfo.getOldPwd());
			if (login == null) {
				msg.setCode(50);
				msg.setMsg("原密码错误");
				return msg;
			}
			//若传入用户ID，则更新用户信息
			userInfo.setPassword(Utils.getMD5(userInfo.getPassword()));
			usermapper.updateUser(userInfo);
			
		} catch (Exception e) {
			msg.setCode(50);
			msg.setMsg("服务器内部错误");
			e.printStackTrace();
		}
		
		return msg;
	}
	
}
