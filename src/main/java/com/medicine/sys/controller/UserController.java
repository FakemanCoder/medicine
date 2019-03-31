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
import com.medicine.sys.model.Message;
import com.medicine.sys.model.PageParam;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.service.UserService;

@Controller
@RequestMapping("api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisUtils redis;
	
	//用户分页
	@RequestMapping("/user_page")
	public String getUserPage(HttpServletResponse response, HttpServletRequest request,ModelMap modelMap, PageParam pageParam, String realname, String mobile, String role) {
		log.info("-->UserController-->getUserPage-->PageParam="+pageParam+",realname="+realname+",mobile="+mobile+",role="+role);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		
		//设置参数
		UserInfo userInfo = new UserInfo();
		userInfo.setRealname(realname);
		userInfo.setMobile(mobile);
		userInfo.setRole(role);
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
			
		}
		//调用用户分页接口
		List<UserInfo> userList = userService.getUserPage(userInfo, pageParam);
		log.info("userList="+userList);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("userList", userList);
		modelMap.addAttribute("pageParam", pageParam);
		modelMap.addAttribute("realname", realname);
		modelMap.addAttribute("mobile", mobile);
		modelMap.addAttribute("role", role);
		
		return "/user/user_list";
	}
	
	//查询个人用户信息
	@RequestMapping("/getUser")
	public String getUser(HttpServletResponse response,HttpServletRequest request,ModelMap modelMap,Long userId) {
		log.info("-->UserController-->getUser-->userId="+userId);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		if (userId==null || userId == 0) {
			userId = Long.valueOf(auth.getUserId());
		}
		UserInfo userInfo = userService.getUser(userId.intValue());
		
		if (userInfo == null) {
			return "user/user_list";
		}
		modelMap.addAttribute("userInfo", userInfo);
		
		return "/user/user_edit";
	}
	
	//编辑用户信息
	@RequestMapping("/editUser")
	public String editUser(HttpServletResponse response,HttpServletRequest request,ModelMap modelMap,UserInfo userInfo) {
		log.info("-->UserController-->editUser-->UserInfo="+userInfo);
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		Message msg = userService.editUser(userInfo);
		modelMap.addAttribute("msg", msg);
		PageParam pageParam = new PageParam();
		int pageNum = 1;
		if (pageParam.getPageNum() > 0) {
			pageNum = pageParam.getPageNum();
			
		}
		List<UserInfo> userList = userService.getUserPage(new UserInfo(), pageParam);
		pageParam.setPageNum(pageNum);
		modelMap.addAttribute("userList", userList);
		modelMap.addAttribute("pageParam", pageParam);
		
		return "/user/user_list";
	}
	
	
	//重置用户密码
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Object resetPwd(HttpServletResponse response, ModelMap modelMap,UserInfo userInfo) {
		log.info("-->UserController-->resetPwd-->UserInfo="+userInfo);
		String pwd = "123456";
		userInfo.setPassword(pwd);
		Message msg = userService.editUser(userInfo);
		msg.setData(pwd);
		
		return msg;
	}
	
	
	
	//修改用户密码
	@RequestMapping("/editPwd")
	@ResponseBody
	public Object editPwd(HttpServletResponse response, ModelMap modelMap,UserInfo userInfo) {
		log.info("-->UserController-->editPwd-->UserInfo="+userInfo);
		Message msg = userService.editPwd(userInfo);
		
		return msg;
	}
	
	//跳转到添加用户页面
	@RequestMapping("/toEditPage")
	public String toEditPage(HttpServletResponse response,HttpServletRequest request,ModelMap modelMap) {
		log.info("-->UserController-->toEditPage-->");
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		modelMap.addAttribute("userInfo", new UserInfo());
		
		return "/user/user_edit";
	}
	
	
	//跳转到添加用户页面
	@RequestMapping("/toEditUserPwd")
	public String toEditUserPwd(HttpServletResponse response,HttpServletRequest request,ModelMap modelMap) {
		log.info("-->UserController-->toEditUserPwd-->");
		UserInfo auth = getAuth(request);
		if (auth == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.addAttribute("auth", auth);
		modelMap.addAttribute("userInfo", new UserInfo());
		
		return "/user/edit_pwd";
	}
	
	//注销或重启用户
	@RequestMapping("/stopUser")
	@ResponseBody
	public Object stopUser(HttpServletResponse response,ModelMap modelMap,int userId, String isDel) {
		log.info("-->UserController-->stopUser-->userId="+userId+",isDel="+isDel);
		
		Message msg = userService.stopUser(userId, isDel);
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
