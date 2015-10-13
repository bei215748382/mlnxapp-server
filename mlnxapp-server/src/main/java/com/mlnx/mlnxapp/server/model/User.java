package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* user 实体类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class User implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String phone;

	@NotNull
	private String password;

	@NotNull
	private Date date;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return password;
	}

	public void setDate(Date date){
		this.date=date;
	}

	public Date getDate(){
		return date;
	}
}

