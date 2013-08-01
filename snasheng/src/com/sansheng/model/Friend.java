package com.sansheng.model;

import com.j256.ormlite.field.DatabaseField;

public class Friend {

	@DatabaseField(generatedId = true)
	private   int id;
	@DatabaseField
	private   String contact_id;
	@DatabaseField
	private   String name;
	@DatabaseField
	private   String cellPhone1;
	@DatabaseField
	private   String cellPhone2;
	@DatabaseField
	private   String telPhone;
	@DatabaseField
	private   String eamil;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellPhone1() {
		return cellPhone1;
	}
	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}
	public String getCellPhone2() {
		return cellPhone2;
	}
	public void setCellPhone2(String cellPhone2) {
		this.cellPhone2 = cellPhone2;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getEamil() {
		return eamil;
	}
	public void setEamil(String eamil) {
		this.eamil = eamil;
	}
	@Override
	public String toString() {
		return "Friend [id=" + id + ", contact_id=" + contact_id + ", name="
				+ name + ", cellPhone1=" + cellPhone1 + ", cellPhone2="
				+ cellPhone2 + ", telPhone=" + telPhone + ", eamil=" + eamil
				+ "]";
	}

 
}
