package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* groupa 实体类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Groupa implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;

	@NotNull
	private int count;

	@NotNull
	private String description;

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

	public void setCount(int count){
		this.count=count;
	}

	public int getCount(){
		return count;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getDescription(){
		return description;
	}
}

