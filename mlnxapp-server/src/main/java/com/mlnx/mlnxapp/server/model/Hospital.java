package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* hospital 实体类
* Wed Sep 30 14:04:34 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Hospital implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;
	
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
	
}

