package com.medicine.sys.model;

public class Message {

	private int code;    //状态码
	private String msg;  //消息体
	private String data; //携带数据
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Message(int code, String msg, String data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public Message(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Message [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
