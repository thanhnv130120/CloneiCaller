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

import java.util.List;

public class Datas {

	@SerializedName("current_page")
	@Expose
	private int currentPage;
	@SerializedName("data")
	@Expose
	private List<Data> data;
	@SerializedName("first_page_url")
	@Expose
	private String firstPageUrl;
	@SerializedName("from")
	@Expose
	private int from;
	@SerializedName("last_page")
	@Expose
	private int lastPage;
	@SerializedName("last_page_url")
	@Expose
	private String lastPageUrl;
	@SerializedName("next_page_url")
	@Expose
	private String nextPageUrl;
	@SerializedName("path")
	@Expose
	private String path;
	@SerializedName("per_page")
	@Expose
	private String perPage;
	@SerializedName("prev_page_url")
	@Expose
	private Object prevPageUrl;
	@SerializedName("to")
	@Expose
	private int to;
	@SerializedName("total")
	@Expose
	private int total;

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return this.data;
	}

	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}

	public String getFirstPageUrl() {
		return this.firstPageUrl;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getFrom() {
		return this.from;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getLastPage() {
		return this.lastPage;
	}

	public void setLastPageUrl(String lastPageUrl) {
		this.lastPageUrl = lastPageUrl;
	}

	public String getLastPageUrl() {
		return this.lastPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	public String getNextPageUrl() {
		return this.nextPageUrl;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setPerPage(String perPage) {
		this.perPage = perPage;
	}

	public String getPerPage() {
		return this.perPage;
	}

	public void setPrevPageUrl(Object prevPageUrl) {
		this.prevPageUrl = prevPageUrl;
	}

	public Object getPrevPageUrl() {
		return this.prevPageUrl;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getTo() {
		return this.to;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return this.total;
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