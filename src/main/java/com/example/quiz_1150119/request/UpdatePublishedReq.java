package com.example.quiz_1150119.request;

import com.example.quiz_1150119.response.BasicRes;

public class UpdatePublishedReq extends BasicRes {
	private int id;
	private boolean published;

	public UpdatePublishedReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdatePublishedReq(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
}
