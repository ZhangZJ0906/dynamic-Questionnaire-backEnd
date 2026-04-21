package com.example.quiz_1150119.response;

import java.util.List;

import com.example.quiz_1150119.enity.Quiz;

public class GetQuizListRes extends BasicRes {
	private List<Quiz> quizList;

	public GetQuizListRes(String message, int code, List<Quiz> quizList) {
		super(message, code);
		this.quizList = quizList;
	}

	public GetQuizListRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}

	public GetQuizListRes(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}
}
