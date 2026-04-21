package com.example.quiz_1150119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1150119.request.CreateQuizReq;
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
	public GetQuizListRes getList() {
		return quizService.getList();
	}

	/** Api url :http://localhost:4200/quiz/get_question_list?quizId=1 */
	@GetMapping(value = "/get_questions")
	public GetQuestionListRes getQuestionsByQuizId(@RequestParam("quizId") int quizId) {
		return quizService.getQuestionsByQuizId(quizId);
	}

}
