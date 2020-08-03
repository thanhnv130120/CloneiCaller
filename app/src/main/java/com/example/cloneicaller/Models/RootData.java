package com.example.cloneicaller.Models;//
//  RootClass.java
//
//  Generated using https://jsonmaster.github.io
//  Created on July 31, 2020
//

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RootData {

	@SerializedName("data")
	@Expose
	private Datas datas;
	@SerializedName("timestamp")
	@Expose
	private String timestamp;
	@SerializedName("phone_deleted")
	@Expose
	private String phoneDeleted;

	public void setDatas(Datas datas) {
		this.datas = datas;
	}

	public Datas getDatas() {
		return this.datas;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setPhoneDeleted(String phoneDeleted) {
		this.phoneDeleted = phoneDeleted;
	}

	public String getPhoneDeleted() {
		return this.phoneDeleted;
	}


	public static RootData create(String json) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, RootData.class);
	}

//	public String toString() {
//		Gson gson = new GsonBuilder().create();
//		return gson.toJson(this);
//	}

}