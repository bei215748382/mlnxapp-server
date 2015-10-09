package com.mlnx.mlnxapp.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * office_doctor 实体类 Wed Sep 30 14:04:34 CST 2015 GenEntityMysql工具类生成
 */
@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Office_doctor implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private int office_id;

	@NotNull
	private int doctor_id;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}

	public int getOffice_id() {
		return office_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}
}
