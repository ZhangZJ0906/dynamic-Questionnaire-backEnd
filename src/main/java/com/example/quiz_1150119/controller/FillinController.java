package com.example.quiz_1150119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1150119.request.FillinReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.response.FeedBackRes;
import com.example.quiz_1150119.response.GetUserWriteQuizIdRes;
import com.example.quiz_1150119.response.StatisticsRes;
import com.example.quiz_1150119.service.FillinService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/fillin")
@CrossOrigin(origins = "http://localhost:4200")
public class FillinController {

	@Autowired
	private FillinService fillinService;

	@PostMapping("/fillin_answer")
	public BasicRes fillin(@Valid @RequestBody FillinReq req) {

		return fillinService.fillin(req);
	}

	@GetMapping("/feed_back")
	public FeedBackRes feedBack(@RequestParam("quizId") int quizId) {

		return fillinService.feedback(quizId);
	}

	@GetMapping("/statistics")
	public StatisticsRes statistics(@RequestParam("quizId") int quizId) {

		return fillinService.statistics(quizId);
	}

	@GetMapping("/get_quiz_id")
	public GetUserWriteQuizIdRes getQuizIdByEmail(@RequestParam("email") String email) {

		return fillinService.getUserWriteQuizId(email);
	}
}
