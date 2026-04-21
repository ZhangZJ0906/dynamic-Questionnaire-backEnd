package com.example.quiz_1150119.vo;

import java.util.List;

import com.example.quiz_1150119.constants.ValidMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class QuestionVo {

	private int quizId;

	@Min(value = 1, message = ValidMessage.QUESTION_ID_ERROR)
	private int questionId;

	@NotBlank(message = ValidMessage.QUESTION_ERROR)
	private String question;

	@NotBlank(message = ValidMessage.TYPE_ERROR)
	private String type;

	private boolean required;

	// 不檢查 簡答題 沒選項
	private List<String> optionsList;

	public QuestionVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public QuestionVo(int quizId, int questionId, String question, String type, boolean required,
			List<String> optionsList) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.optionsList = optionsList;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<String> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<String> optionsList) {
		this.optionsList = optionsList;
	}
}
