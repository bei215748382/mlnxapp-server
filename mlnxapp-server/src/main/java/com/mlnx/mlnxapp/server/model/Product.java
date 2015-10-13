package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* product 实体类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Product implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String title;

	@NotNull
	private String body;

	@NotNull
	private String owner;

	@NotNull
	private String pic;

	@NotNull
	private String specifications;

	private String type;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body=body;
	}

	public String getBody(){
		return body;
	}

	public void setOwner(String owner){
		this.owner=owner;
	}

	public String getOwner(){
		return owner;
	}

	public void setPic(String pic){
		this.pic=pic;
	}

	public String getPic(){
		return pic;
	}

	public void setSpecifications(String specifications){
		this.specifications=specifications;
	}

	public String getSpecifications(){
		return specifications;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}
}

