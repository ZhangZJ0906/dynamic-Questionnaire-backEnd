package com.example.quiz_1150119.request;

import java.util.List;

import com.example.quiz_1150119.constants.ValidMessage;
import com.example.quiz_1150119.vo.AnswersVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class FillinReq {

	@Min(value = 1, message = ValidMessage.QUIZ_ID_ERROR)
	private int quizId;

	@NotEmpty(message = ValidMessage.EMAIL_ERROR)
	private String email;

	/* 極端值 可能會全都非必填 跟沒回答 */
	@Valid // 嵌套驗證因為AnswersVo中的questionId 有檢察
	private List<AnswersVo> answersVos;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AnswersVo> getAnswersVos() {
		return answersVos;
	}

	public void setAnswersVos(List<AnswersVo> answersVos) {
		this.answersVos = answersVos;
	}


}
