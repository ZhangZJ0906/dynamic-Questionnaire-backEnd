package com.example.quiz_1150119.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz_1150119.constants.ReplyMessage;
import com.example.quiz_1150119.constants.Type;
import com.example.quiz_1150119.dao.FillinDao;
import com.example.quiz_1150119.dao.QuestionDao;
import com.example.quiz_1150119.dao.QuizDao;
import com.example.quiz_1150119.enity.Fillin;
import com.example.quiz_1150119.enity.Question;
import com.example.quiz_1150119.enity.Quiz;
import com.example.quiz_1150119.request.FillinReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.vo.AnswersVo;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class FillinService {
	/* 物件 字串的轉換工具 */
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

	public BasicRes fillin(FillinReq req) {
		int quizId = req.getQuizId();
		LocalDate today = LocalDate.now();
		Quiz quiz = quizDao.getPublishedQuizBetween(quizId, today);
		if (quiz == null) {
			return new BasicRes(ReplyMessage.QUIZ_NOT_FOUND.getMessage(), ReplyMessage.QUIZ_NOT_FOUND.getCode());
		}
		/* answerVo to Map */
		Map<Integer, List<String>> voMap = new HashMap<>();
		for (AnswersVo answersVo : req.getAnswersVos()) {
			voMap.put(answersVo.getQuestionId(), answersVo.getAnswerList());
		}

//		Lambda 表達式: List 轉成 Map
//		Map<Integer, List<String>> voMap = req.getAnswersVoList().stream().collect(Collectors.toMap( AnswersVo::getQuestionId, // Key: Question ID AnswersVo::getAnswersList // Value: 答案列表 ));

		/* 檢查 question */
		List<Question> questions = questionDao.getByQuizId(quizId);
		for (Question question : questions) {
			/** 必填沒有答案 */
			if (question.isRequired() && !voMap.containsKey(question.getQuestionId())) {
				return new BasicRes(ReplyMessage.ANSWER_IS_REQUIRED.getMessage(),
						ReplyMessage.ANSWER_IS_REQUIRED.getCode());

			}
			List<String> anwserList = voMap.get(question.getQuestionId());
			/* 單選USER選多選項 */
			if (Type.isSingleType(question.getType()) && anwserList.size() > 1) {
				return new BasicRes(ReplyMessage.ONLY_ONe_ANSWER_ALLOW.getMessage(),
						ReplyMessage.ONLY_ONe_ANSWER_ALLOW.getCode());
			}
			/* 檢查是選擇類型，答案跟選項要符合 --> 拿答案來跟選項比對 */
			/*
			 * 1. 先把 Question 中的 options 字串轉換成 List<String>， 因為寫進 DB 的字串值 就是由 List<String>
			 * 轉換來的
			 */
			try {
				List<String> optionsList = mapper.readValue(question.getOptions(), new TypeReference<>() {
				});
				/* 答案跟選項比對 */
				/* 大集合(有完整資訊的).containsAll(小集合)(小集合為USER提供的答案) */
				if (!optionsList.containsAll(anwserList)) {
					return new BasicRes(ReplyMessage.OPTION_ANSWER_MISMATCH.getMessage(),
							ReplyMessage.OPTION_ANSWER_MISMATCH.getCode());
				}

			} catch (Exception e) {
				// TODO: handle exception
				return new BasicRes(ReplyMessage.OPTIONS_PARSER_ERROR.getMessage(),
						ReplyMessage.OPTIONS_PARSER_ERROR.getCode());
			}
			/* 使用 spring jpa 的save() */
			/*
			 * spring date jpa 的 save(): 新增與更新都使用這個方法，在執行 save() 方法時，會先利用 entity 的 ID(PK)
			 * 來查詢資料是否存在；若存在就執行更新，不存在就執行新增
			 */
			for (AnswersVo vo : req.getAnswersVos()) {
				try {
					String str=mapper.writeValueAsString(vo.getAnswerList());
					Fillin fillin = new Fillin(quizId, vo.getQuestionId(), req.getEmail(), str, today);
					fillinDao.save(fillin);
				} catch (Exception e) {
					return new BasicRes(ReplyMessage.ANSWERS_PARSER_ERROR.getMessage(),
							ReplyMessage.ANSWERS_PARSER_ERROR.getCode());
				}

			}

		}

		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}
}
