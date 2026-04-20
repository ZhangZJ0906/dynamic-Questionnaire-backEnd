package com.example.quiz_1150119.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.quiz_1150119.constants.ReplyMessage;
import com.example.quiz_1150119.constants.Type;
import com.example.quiz_1150119.dao.QuizDao;
import com.example.quiz_1150119.dao.QusetionDao;
import com.example.quiz_1150119.enity.Quiz;
import com.example.quiz_1150119.request.CreateQuizReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.vo.QuestionVo;

import tools.jackson.databind.ObjectMapper;

@Service
public class QuizService {
	/* 物件 字串的轉換工具 */
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QusetionDao qusetionDao;

	public BasicRes create(CreateQuizReq req) {
		Quiz quiz = req.getQuiz();
		/* start 不能再 end 之後 */
		if (quiz.getStartDate().isAfter(quiz.getEndDate())) {
			return new BasicRes(ReplyMessage.START_DATE_IS＿AFTER_END_DATE.getMessage(),
					ReplyMessage.START_DATE_IS＿AFTER_END_DATE.getCode());
		}

		if (quiz.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ReplyMessage.START_DATE_IS＿AFTER_TODAY.getMessage(),
					ReplyMessage.START_DATE_IS＿AFTER_TODAY.getCode());
		}

		for (QuestionVo questionVo : req.getQuestionVoList()) {
			if (Type.check(questionVo.getType()) == false) {
				return new BasicRes(ReplyMessage.TYPE_ERROR.getMessage(), ReplyMessage.TYPE_ERROR.getCode());

			}
			if (Type.isChoice(questionVo.getType()) && CollectionUtils.isEmpty(questionVo.getOptionsList())) {
				return new BasicRes(ReplyMessage.OPTIONS_IS_EMPT.getMessage(), ReplyMessage.OPTIONS_IS_EMPT.getCode());
			}
		}

		quizDao.insert(quiz.getTitle(), quiz.getDescription(), quiz.getStartDate(), quiz.getEndDate(),
				quiz.isPublished());
		int quizId = quizDao.getMaxId();
		for (QuestionVo questionVo : req.getQuestionVoList()) {

			try {
				String options = mapper.writeValueAsString(questionVo.getOptionsList());
				qusetionDao.insert(quizId, questionVo.getQuestionId(), questionVo.getQuestion(), questionVo.getType(),
						questionVo.isRequired(), options);


			} catch (Exception e) {
				return new BasicRes(ReplyMessage.OPTIONS_TRANSFER_ERROR.getMessage(),
						ReplyMessage.OPTIONS_TRANSFER_ERROR.getCode());
			}
		}
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}
}
