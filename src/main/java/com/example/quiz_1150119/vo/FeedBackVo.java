package com.example.quiz_1150119.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz_1150119.enity.User;

/*1個 FeedBackVo 對應一個USER填答*/
public class FeedBackVo {
	private User user;
	private LocalDate fillinDate;
	private List<AnswersVo> answersVos;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	public List<AnswersVo> getAnswersVos() {
		return answersVos;
	}

	public void setAnswersVos(List<AnswersVo> answersVos) {
		this.answersVos = answersVos;
	}

	public FeedBackVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedBackVo(User user, LocalDate fillinDate, List<AnswersVo> answersVos) {
		super();
		this.user = user;
		this.fillinDate = fillinDate;
		this.answersVos = answersVos;
	}

}
