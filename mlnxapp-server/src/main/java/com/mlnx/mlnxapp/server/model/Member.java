package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* member 实体类
* Wed Sep 30 14:04:34 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Member implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String email;

	private String phoneNumber;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return email;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber=phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}
}

