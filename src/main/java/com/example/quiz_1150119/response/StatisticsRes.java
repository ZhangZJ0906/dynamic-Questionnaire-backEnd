package com.example.quiz_1150119.response;

import java.util.List;

import com.example.quiz_1150119.vo.AnswersVo;

public class StatisticsRes extends BasicRes {
	private List<AnswersVo> answersVos;

	public StatisticsRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatisticsRes(String message, int code) {
		super(message, code);
		// TODO Auto-generated constructor stub
	}

	public StatisticsRes(String message, int code, List<AnswersVo> answersVos) {
		super(message, code);
		this.answersVos = answersVos;
	}

	public List<AnswersVo> getAnswersVos() {
		return answersVos;
	}

	public void setAnswersVos(List<AnswersVo> answersVos) {
		this.answersVos = answersVos;
	}
}
