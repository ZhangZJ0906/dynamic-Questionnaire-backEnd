package com.example.quiz_1150119.response;

import java.util.List;

public class GetUserWriteQuizIdRes extends BasicRes {
	private List<Integer> quizId;

	public List<Integer> getQuizId() {
		return quizId;
	}

	public void setQuizId(List<Integer> quizId) {
		this.quizId = quizId;
	}

	public GetUserWriteQuizIdRes(String message, int code, List<Integer> quizId) {
		super(message, code);
		this.quizId = quizId;
	}

	public GetUserWriteQuizIdRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetUserWriteQuizIdRes(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}

}
