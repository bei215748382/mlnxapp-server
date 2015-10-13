package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* article 实体类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Article implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String title;

	@NotNull
	private String body;

	@NotNull
	private String author;

	@NotNull
	private Date time;

	@NotNull
	private String type;

	private int praise;

	private int collect;

	private int comment;

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

	public void setTime(Date time){
		this.time=time;
	}

	public Date getTime(){
		return time;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setPraise(int praise){
		this.praise=praise;
	}

	public int getPraise(){
		return praise;
	}

	public void setCollect(int collect){
		this.collect=collect;
	}

	public int getCollect(){
		return collect;
	}

	public void setComment(int comment){
		this.comment=comment;
	}

	public int getComment(){
		return comment;
	}
}

