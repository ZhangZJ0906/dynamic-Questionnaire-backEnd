package com.example.quiz_1150119.vo;

import java.util.List;

import com.example.quiz_1150119.constants.ValidMessage;

import jakarta.validation.constraints.Min;

public class AnswersVo {
	// 一個answersVo 表是一個問題跟達案
	@Min(value = 1, message = ValidMessage.QUESTION_ID_ERROR)
	private int questionId;
	private List<String> answerList;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}
}
