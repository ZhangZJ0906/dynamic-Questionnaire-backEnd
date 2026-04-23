package com.example.quiz_1150119.enity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "fillin")
@IdClass(value = FillinId.class)
public class Fillin {

	public Fillin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fillin(int quizId, int questionId, String email, String answers, LocalDate fillin_date) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.email = email;
		this.answers = answers;
		this.fillin_date = fillin_date;
	}

	@Id
	@Column(name = "quiz_id")
	private int quizId;

	@Id
	@Column(name = "question_id")
	private int questionId;

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "answers")
	private String answers;

	@Column(name = "fillin_date")
	private LocalDate fillin_date;



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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public LocalDate getFillin_date() {
		return fillin_date;
	}

	public void setFillin_date(LocalDate fillin_date) {
		this.fillin_date = fillin_date;
	}
}
