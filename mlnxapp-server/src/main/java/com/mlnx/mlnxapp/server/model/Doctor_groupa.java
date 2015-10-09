package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
/**
* doctor_groupa 实体类
* Wed Sep 30 14:04:34 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Doctor_groupa implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private int doctor_id;

	@NotNull
	private int group_id;

	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}

	public void setDoctor_id(int doctor_id){
		this.doctor_id=doctor_id;
	}

	public int getDoctor_id(){
		return doctor_id;
	}

	public void setGroup_id(int group_id){
		this.group_id=group_id;
	}

	public int getGroup_id(){
		return group_id;
	}
}

