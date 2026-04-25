package com.example.quiz_1150119.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1150119.request.CreateQuizReq;
import com.example.quiz_1150119.request.UpdatePublishedReq;
import com.example.quiz_1150119.request.UpdateQuizReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.response.GetQuestionListRes;
import com.example.quiz_1150119.response.GetQuizListRes;
import com.example.quiz_1150119.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "quiz")
@CrossOrigin(origins = "http://localhost:4200")
public class QuizController {

	@Autowired
	private QuizService quizService;


	@PostMapping(value = "/create")
	public BasicRes create(@Valid @RequestBody CreateQuizReq req) {
		return quizService.create(req);
	}

	@PostMapping(value = "/update")
	public BasicRes update(@Valid @RequestBody UpdateQuizReq req) {
		return quizService.update(req);
	}

	@GetMapping(value = "/get_quiz_list")
	public GetQuizListRes getList(@RequestParam("isFrontEnd") boolean isFrontEnd) {
		return quizService.getList(isFrontEnd);
	}

	/** Api url :http://localhost:4200/quiz/get_question_list?quizId=1 */
	@GetMapping(value = "/get_questions")
	public GetQuestionListRes getQuestionsByQuizId(@RequestParam("quizId") int quizId) {
		return quizService.getQuestionsByQuizId(quizId);
	}

	/*
	 * 相同的 key 但會有多個值時，API 路徑，問號後面的格式會是: ?quizId=1&quizId=2&quizId=3， 多個參數時用&串接
	 */

	@GetMapping(value = "/delete")
	public BasicRes deleteQuiz(@RequestParam("quizId") List<Integer> quizIdList) {
		return quizService.deleteQuiz(quizIdList);
	}

	/* 自己寫的 */
	@PostMapping(value = "/update_published")
	public BasicRes updatePublishedReq(@RequestBody UpdatePublishedReq req) {
		return quizService.updatePublishedReq(req);

	}

	@GetMapping(value = "/get_quiz_by_id")
	public GetQuizListRes getQuizById(@RequestParam("quizId") int quizId) {
		return quizService.getQuizById(quizId);

	}


}
