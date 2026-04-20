package com.example.quiz_1150119.enity;

import java.time.LocalDate;

import com.example.quiz_1150119.constants.ValidMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "quiz")
public class Quiz {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // 參考PPT spring 基礎001 專題練習三吧 18~21 PAGE 
	@Column(name = "id")
	private int id ;

	/*
	 * @NotBlank: 限制屬性的值不能是 null, 空字串, 全空白字串，效果同 !StringUtils.hasText， message
	 * 等號後面的字串指的是當違反限制時會得到的訊息，必須是 final，static 則是可以直接透過 類別名稱去呼叫使用
	 */

	@NotBlank(message = ValidMessage.TITLE_ERROR)
	@Column(name = "title")
	private String title;

	@NotBlank(message = ValidMessage.DESCRITION_ERROR)
	@Column(name = "description")
	private String description;

	@NotNull(message = ValidMessage.START_DATE_ERROR)
	@Column(name = "start_date")
	private LocalDate startDate;

	@NotNull(message = ValidMessage.END_DATE_ERROR)
	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "published")
	private boolean published;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
