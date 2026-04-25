package com.example.quiz_1150119.response;

import java.util.List;

import com.example.quiz_1150119.vo.FeedBackVo;

public class FeedBackRes extends BasicRes {
	private int quizId;

	private List<FeedBackVo> feedBackVoList;

	public FeedBackRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedBackRes(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}

	public FeedBackRes(String message, int code, int quizId, List<FeedBackVo> feedBackVoList) {
		super(message, code);
		this.quizId = quizId;
		this.feedBackVoList = feedBackVoList;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public List<FeedBackVo> getFeedBackVoList() {
		return feedBackVoList;
	}

	public void setFeedBackVoList(List<FeedBackVo> feedBackVoList) {
		this.feedBackVoList = feedBackVoList;
	}

}
