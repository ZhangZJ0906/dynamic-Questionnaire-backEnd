package com.example.quiz_1150119.constants;

public enum ReplyMessage {
	SUCCESS(200, "Success"), //
	PARAM_EMAIL_ERROR(400, "Param email error"), //
	PARAM_NAME_ERROR(400, "Param name error"), //
	PARAM_AGE_ERROR(400, "Param age error"),//
	EMAIL_NOT_FOUND(404, "Param email not found "),//
	START_DATE_IS＿AFTER_END_DATE(400, "start date is after end date "),//
	START_DATE_IS＿AFTER_TODAY(400, "start date is after today  "),//
	TYPE_ERROR(400, "type error"),//
	OPTIONS_IS_EMPT(400, "option is empty"),//
	OPTIONS_TRANSFER_ERROR(400, "option transfer error")
	;

	private int code;

	private String message;

	private ReplyMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
