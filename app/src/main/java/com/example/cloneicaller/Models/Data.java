package com.example.cloneicaller.Models;//
//  Data.java
//
//  Generated using https://jsonmaster.github.io
//  Created on July 31, 2020
//

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {

	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("code")
	@Expose
	private String code;
	@SerializedName("phone")
	@Expose
	private String phone;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("warn_type")
	@Expose
	private int warnType;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	@SerializedName("user")
	@Expose
	private Object user;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setWarnType(int warnType) {
		this.warnType = warnType;
	}

	public int getWarnType() {
		return this.warnType;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public Object getUser() {
		return this.user;
	}


	public static Datas create(String json) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, Datas.class);
	}

//	public String toString() {
//		Gson gson = new GsonBuilder().create();
//		return gson.toJson(this);
//	}

}