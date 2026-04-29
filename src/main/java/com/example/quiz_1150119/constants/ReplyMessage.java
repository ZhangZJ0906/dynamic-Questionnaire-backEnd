package com.example.quiz_1150119.constants;

public enum ReplyMessage {
	SUCCESS(200, "Success"), //
	PARAM_EMAIL_ERROR(400, "Param email error"), //
	PARAM_NAME_ERROR(400, "Param name error"), //
	PARAM_AGE_ERROR(400, "Param age error"),//
	EMAIL_NOT_FOUND(404, "Param email not found "),//
	EMAIL_IS_REPEAT(400, "email is repeat "), //
	START_DATE_IS＿AFTER_END_DATE(400, "start date is after end date "),//
	START_DATE_IS＿AFTER_TODAY(400, "start date is after today  "),//
	TYPE_ERROR(400, "type error"),//
	OPTIONS_IS_EMPT(400, "option is empty"),//
	OPTIONS_PARSER_ERROR(400, "option parser error"), //
	QUIZ_ID_ERROR(400, "Quiz id error"),//
	QUIZ_ID_MISMATCH(400, "quiz id is mismatch"),//
	QUIZ_NOT_FOUND(404, " quiz not found "),//
	ANSWER_IS_REQUIRED(400, " answer is required "),//
	ONLY_ONe_ANSWER_ALLOW(400, "only one  answer  allow  "), //
	OPTION_ANSWER_MISMATCH(400, " options answer mismatch "),//
	ANSWERS_PARSER_ERROR(400, "answers parser error"), //
	QUIZ_UPDATE_NOT_ALLOW(400, "QUIZ UPDATE NOT ALLOW"), //
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
