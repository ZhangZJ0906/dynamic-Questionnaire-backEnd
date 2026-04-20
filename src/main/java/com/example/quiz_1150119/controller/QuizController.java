package com.example.quiz_1150119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1150119.request.CreateQuizReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "quiz")
public class QuizController {
	@Autowired
	private QuizService quizService;

	@PostMapping(value = "/create")
	public BasicRes create(@Valid @RequestBody CreateQuizReq req) {
		return quizService.create(req);
	}
}
