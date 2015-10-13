package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* comment 实体类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Comment implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String title;

	@NotNull
	private String body;

	@NotNull
	private int star;

	@NotNull
	private int punctual;

	@NotNull
	private int attitude;

	@NotNull
	private int skill;

	@NotNull
	private Date time;

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

	public void setStar(int star){
		this.star=star;
	}

	public int getStar(){
		return star;
	}

	public void setPunctual(int punctual){
		this.punctual=punctual;
	}

	public int getPunctual(){
		return punctual;
	}

	public void setAttitude(int attitude){
		this.attitude=attitude;
	}

	public int getAttitude(){
		return attitude;
	}

	public void setSkill(int skill){
		this.skill=skill;
	}

	public int getSkill(){
		return skill;
	}

	public void setTime(Date time){
		this.time=time;
	}

	public Date getTime(){
		return time;
	}
}

