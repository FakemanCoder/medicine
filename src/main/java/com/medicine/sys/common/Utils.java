package com.medicine.sys.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;


public class Utils {

	//MD5加密
	public static String getMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (str == null || "".equals(str)) {
			return null;
		}
		byte[] hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
	}
	
	/**
	 * 获取登陆者信息
	 * @param request
	 * @return
	 */
	public static String getAuth(HttpServletRequest request){
		Object keyObj = request.getSession().getAttribute("loginName");
		if (keyObj == null || keyObj.toString() == null) {
			return null;
		}
//		RedisUtils redis = new RedisUtils();
//		String json = redis.get(keyObj.toString());
//		UserInfo user = JSON.parseObject(json, UserInfo.class);
		return keyObj.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getMD5("123456"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
