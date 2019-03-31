package com.medicine.sys.model;

import java.util.Date;

public class UserInfo {

	private int userId;       //用户ID
	private String realname;  //用户姓名
	private String username;  //用户账号
	private String mobile;    //用户手机号码
	private String isDel;     //是否删除
	private String password;  //密码
	private String oldPwd;    //旧密码
	private String email;     //用户邮件
	private String address;   //用户地址
	private String role;      //用户角色
	private String roleName;      //用户角色
	private int operator;     //操作人
	private Date operatdate;  //操作时间
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getOperatdate() {
		return operatdate;
	}
	public void setOperatdate(Date operatdate) {
		this.operatdate = operatdate;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", realname=" + realname + ", username=" + username + ", mobile=" + mobile
				+ ", isDel=" + isDel + ", password=" + password + ", oldPwd=" + oldPwd + ", email=" + email
				+ ", address=" + address + ", role=" + role + ", roleName=" + roleName + ", operator=" + operator
				+ ", operatdate=" + operatdate + "]";
	}
}
