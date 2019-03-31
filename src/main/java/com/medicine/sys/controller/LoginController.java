package com.medicine.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.medicine.sys.common.RedisUtils;
import com.medicine.sys.common.Utils;
import com.medicine.sys.model.Message;
import com.medicine.sys.model.UserInfo;
import com.medicine.sys.service.UserService;

@Controller
@RequestMapping("/loginapi")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisUtils redis;
	
	//登录
	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap, String username, String password) {
		log.info("-->LoginController-->login-->username="+username+",password="+password);
		try {
			//校验用户账号与密码
			Message msg = new Message(-1, "登录失败");
			UserInfo userInfo = userService.login(username, password);
			if (userInfo == null) {
				return msg;
			}
			//设置redis缓存
			String keyValue = Utils.getMD5(userInfo.getUserId()+userInfo.getMobile()+System.currentTimeMillis());
			String json = new Gson().toJson(userInfo);
			redis.set(keyValue, json);
			request.getSession().setAttribute("loginName", keyValue);
//			response.setHeader("SET-COOKIE",  "key="+ keyValue + ";Path=/;domain=localhost:8080;date="+DateUtils.addNowDate(0, 0, 1));
			modelMap.addAttribute("userInfo",userInfo);
			msg.setCode(0);
			msg.setMsg("登录成功");
			return msg;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "order/add_order";
	}
	
	
	//退出登录
	@RequestMapping("login_out")
	public String loginOut(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) {
		log.info("-->LoginController-->loginOut-->");
		try {

			Object key = request.getSession().getAttribute("loginName");
			if (key != null ) {
				redis.del(key.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/";
	}
	
}
