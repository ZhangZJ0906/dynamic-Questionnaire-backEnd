package com.example.quiz_1150119.response;

import java.util.List;

import com.example.quiz_1150119.vo.QuestionVo;

public class GetQuestionListRes extends BasicRes {
	private List<QuestionVo> questionVos;

	public GetQuestionListRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetQuestionListRes(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}

	public GetQuestionListRes(String message, int code, List<QuestionVo> questionVos) {
		super(message, code);
		this.questionVos = questionVos;
	}

	public List<QuestionVo> getQuestionVos() {
		return questionVos;
	}

	public void setQuestionVos(List<QuestionVo> questionVos) {
		this.questionVos = questionVos;
	}
}
