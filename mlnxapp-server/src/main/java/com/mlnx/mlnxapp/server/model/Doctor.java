package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* doctor 实体类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Doctor implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;

	@NotNull
	private int office;

	@NotNull
	private String title;

	@NotNull
	private int hospital;

	private int order_;

	private int consult;

	private int group_;

	@NotNull
	private String skill;

	@NotNull
	private int comment;

	private int commentlist;

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

	public void setOffice(int office){
		this.office=office;
	}

	public int getOffice(){
		return office;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setHospital(int hospital){
		this.hospital=hospital;
	}

	public int getHospital(){
		return hospital;
	}

	public void setOrder_(int order_){
		this.order_=order_;
	}

	public int getOrder_(){
		return order_;
	}

	public void setConsult(int consult){
		this.consult=consult;
	}

	public int getConsult(){
		return consult;
	}

	public void setGroup_(int group_){
		this.group_=group_;
	}

	public int getGroup_(){
		return group_;
	}

	public void setSkill(String skill){
		this.skill=skill;
	}

	public String getSkill(){
		return skill;
	}

	public void setComment(int comment){
		this.comment=comment;
	}

	public int getComment(){
		return comment;
	}

	public void setCommentlist(int commentlist){
		this.commentlist=commentlist;
	}

	public int getCommentlist(){
		return commentlist;
	}
}

