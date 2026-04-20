package com.example.quiz_1150119.enity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 必須實作 Serializable 1. 此 class 主要是管理一個 Entity 中的多個 PK 屬性<br>
 * 2. 必須要實作介面序列化<br>
 * 2.1 但因序列化中沒有定義任何方法，所以不需要重新定義任何的實作<br>
 * 3. 必須實作 equals 和 hashCode : 3.1 JPA 靠這兩個方法來比對兩個 ID
 * 是否代表同一個實體；如果不寫，會導致快取失效或找不到資料<br>
 * 4. 建議加上無參數和有參數的建構方法
 */
@SuppressWarnings("serial")
public class QusetionId implements Serializable {
	private int quizId;

	private int questionId;

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

	@Override
	public boolean equals(Object o) {
		/* 檢查是否為同一個記憶體位址 */
		if (this == o)
			return true;
		/* 檢查物件是否為 null 或類別不一致 */
		if (o == null || getClass() != o.getClass())
			return false;
		/* 轉型後比較欄位內容 */
		QusetionId that = (QusetionId) o;
		return quizId == that.quizId && questionId == that.questionId;
	}

	@Override
	public int hashCode() {
		// 根據欄位內容產生 Hash 值
		return Objects.hash(quizId, questionId);
	}

}

