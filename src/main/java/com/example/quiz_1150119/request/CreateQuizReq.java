package com.example.quiz_1150119.request;

import java.util.List;

import com.example.quiz_1150119.constants.ValidMessage;
import com.example.quiz_1150119.enity.Quiz;
import com.example.quiz_1150119.vo.QuestionVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateQuizReq {
	/*
	 * 檢查的對象是基本資料型態時
	 * 
	 * @Min(18) // private int age;
	 */

	/* 檢查的對象是字串時 */
//	@NotBlank(message = "字串不能為空!!")
//	private String test;

	/*
	 * 【嵌套驗證說明】 當被檢查的屬性是「自定義類別」（例如 CreateQuizReq 中的對象屬性）時： * 1.
	 * 類別層級驗證：該屬性作為父類別的成員，需確保其本身不為 null。 2. 屬性層級驗證：必須深入檢查該自定義類別內部的各個欄位限制。 3.
	 * 啟動嵌套驗證：必須在該屬性上標註 @Valid，否則該類別內部的驗證註解（如 @NotBlank）將不會生效。
	 */
	@Valid
	@NotNull(message = ValidMessage.QUIZ_IS_NULL)
	private Quiz quiz;

	/* 限制屬性不是null 長度不為0 */
	@NotEmpty(message = ValidMessage.QUESTION_VO_IS_EMPTY)
	private List<QuestionVo> questionVoList;

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public List<QuestionVo> getQuestionVoList() {
		return questionVoList;
	}

	public void setQuestionVoList(List<QuestionVo> questionVoList) {
		this.questionVoList = questionVoList;
	}

}
