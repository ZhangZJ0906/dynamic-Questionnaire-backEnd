package com.example.quiz_1150119.vo;

import java.util.List;

import com.example.quiz_1150119.constants.ValidMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AnswersVo {
	// 一個answersVo 表是一個問題跟達案
	@Min(value = 1, message = ValidMessage.QUESTION_ID_ERROR)
	private int questionId;
	@NotBlank
	private String questionTitle; // 新增欄位
	private List<String> answerList;

	public AnswersVo(int questionId, List<String> answerList) {
		super();
		this.questionId = questionId;
		this.answerList = answerList;
	}

	public AnswersVo(int questionId, String questionTitle, List<String> answerList) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.answerList = answerList;
	}

	public AnswersVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

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
