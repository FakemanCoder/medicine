package com.medicine.sys.db;

public class User {

	private int user_id; 
	private String realname;
	private String username;
	private String mobile;
	private String password;
	private String is_del;
	private String email;
	private String address;
	private String role;
	private int createby;
	private String createdate;
	private int updateby;
	private String updatedate;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getCreateby() {
		return createby;
	}
	public void setCreateby(int createby) {
		this.createby = createby;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getUpdateby() {
		return updateby;
	}
	public void setUpdateby(int updateby) {
		this.updateby = updateby;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", realname=" + realname + ", username=" + username + ", mobile=" + mobile
				+ ", password=" + password + ", is_del=" + is_del + ", email=" + email + ", address=" + address
				+ ", role=" + role + ", createby=" + createby + ", createdate=" + createdate + ", updateby=" + updateby
				+ ", updatedate=" + updatedate + "]";
	}
}
