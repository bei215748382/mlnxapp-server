package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* question 实体类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Question implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	private String title;

	@NotNull
	private String body;

	@NotNull
	private String author;

	@NotNull
	private String state;

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

	public void setAuthor(String author){
		this.author=author;
	}

	public String getAuthor(){
		return author;
	}

	public void setState(String state){
		this.state=state;
	}

	public String getState(){
		return state;
	}

	public void setTime(Date time){
		this.time=time;
	}

	public Date getTime(){
		return time;
	}
}

