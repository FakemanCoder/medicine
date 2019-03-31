package com.medicine.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//启动工程
@SpringBootApplication
@MapperScan("com.medicine.sys.mapper")
@ServletComponentScan
public class MedicineSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicineSysApplication.class, args);
	}

}
