package com.example.quiz_1150119.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.quiz_1150119.constants.ReplyMessage;
import com.example.quiz_1150119.constants.Type;
import com.example.quiz_1150119.dao.QuestionDao;
import com.example.quiz_1150119.dao.QuizDao;
import com.example.quiz_1150119.enity.Question;
import com.example.quiz_1150119.enity.Quiz;
import com.example.quiz_1150119.request.CreateQuizReq;
import com.example.quiz_1150119.request.UpdatePublishedReq;
import com.example.quiz_1150119.request.UpdateQuizReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.response.GetQuestionListRes;
import com.example.quiz_1150119.response.GetQuizListRes;
import com.example.quiz_1150119.vo.QuestionVo;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class QuizService {
	/* 物件 字串的轉換工具 */
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;
	/*
	 * 一個方法中若有使用到多個 Dao 或是同一個 Dao 有呼叫多次去對資料作變更(新增、修改、刪除)， 必須要用@Transactional，因為這些
	 * Dao 的操作，都屬於同一次的操作，因此資料的變更要嘛全部成功， 不然就全部都不成功，回溯到尚未變更之前
	 */
	/*
	 * rollbackFor = Exception.class: 作用在於把原本只有發生 RuntimeException 及其子類別的例外時，
	 * 
	 * @Transactional 才會有效用，提升到只要是發生任何的 Exception 時，@Transactional 都會有效用
	 */

	@Transactional(rollbackFor = Exception.class)
	public BasicRes create(CreateQuizReq req) throws Exception {
		Quiz quiz = req.getQuiz();
		List<QuestionVo> questionVoList = req.getQuestionVoList();
		BasicRes checkRes = checkDateAndType(quiz, questionVoList);
		/* start 不能再 end 之後 */
		if (checkRes != null) {
			return checkRes;
		}

		quizDao.insert(quiz.getTitle(), quiz.getDescription(), quiz.getStartDate(), quiz.getEndDate(),
				quiz.isPublished());
		int quizId = quizDao.getMaxId();

		for (QuestionVo questionVo : questionVoList) {
			/* 3. 把 List<String> 轉換成 String，因為 MySQL 不能存 List */
			// 類別中都會有 toString() 這個方法，這裡不能使用 toString() 將 vo.getOptionsList()
			// 直接轉成字串，因為使用此方式轉換得到的字串無法再被轉回成原本的資料型態(基本資料型態除外)
			try {
				String options = mapper.writeValueAsString(questionVo.getOptionsList());
				questionDao.insert(quizId, questionVo.getQuestionId(), questionVo.getQuestion(), questionVo.getType(),
						questionVo.isRequired(), options);

			} catch (Exception e) {
//				return new BasicRes(ReplyMessage.OPTIONS_PARSER_ERROR.getMessage(),
//						ReplyMessage.OPTIONS_PARSER_ERROR.getCode());
				throw e;
			}
		}
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}

	private BasicRes checkDateAndType(Quiz quiz, List<QuestionVo> questionVoList) {

		/* start 不能再 end 之後 */
		if (quiz.getStartDate().isAfter(quiz.getEndDate())) {
			return new BasicRes(ReplyMessage.START_DATE_IS＿AFTER_END_DATE.getMessage(),
					ReplyMessage.START_DATE_IS＿AFTER_END_DATE.getCode());
		}

		if (quiz.getStartDate().isBefore(LocalDate.now())) {
			return new BasicRes(ReplyMessage.START_DATE_IS＿AFTER_TODAY.getMessage(),
					ReplyMessage.START_DATE_IS＿AFTER_TODAY.getCode());
		}

		for (QuestionVo questionVo : questionVoList) {
			if (Type.check(questionVo.getType()) == false) {
				return new BasicRes(ReplyMessage.TYPE_ERROR.getMessage(), ReplyMessage.TYPE_ERROR.getCode());

			}
			if (Type.isChoice(questionVo.getType()) && CollectionUtils.isEmpty(questionVo.getOptionsList())) {
				return new BasicRes(ReplyMessage.OPTIONS_IS_EMPT.getMessage(), ReplyMessage.OPTIONS_IS_EMPT.getCode());
			}
		}

		return null;
	}

	@Transactional
	public BasicRes update(UpdateQuizReq req) {

		Quiz quiz = req.getQuiz();
		int quizId = quiz.getId();
		List<QuestionVo> questionVoList = req.getQuestionVoList();

		if (quizId <= 0) {
			return new BasicRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(), ReplyMessage.QUIZ_ID_ERROR.getCode());
		}
		for (QuestionVo questionVo : req.getQuestionVoList()) {
			if (quizId != questionVo.getQuizId()) {
				return new BasicRes(ReplyMessage.QUIZ_ID_MISMATCH.getMessage(),
						ReplyMessage.QUIZ_ID_MISMATCH.getCode());
			}
		}
		BasicRes checkRes = checkDateAndType(quiz, questionVoList);
		/* start 不能再 end 之後 */
		if (checkRes != null) {
			return checkRes;
		}
		/* 檢查問卷是否可以更新: 問卷是已發布且今天不能是在開始日期當天以及之後 */
		if (quizDao.getPublishedQuizAfter(quizId, LocalDate.now()) != null) {// 等於有資料
			return new BasicRes(ReplyMessage.QUIZ_UPDATE_NOT_ALLOW.getMessage(),
					ReplyMessage.QUIZ_UPDATE_NOT_ALLOW.getCode());
		}
		// 更新quiz
		quizDao.update(quizId, quiz.getTitle(), quiz.getDescription(), //
				quiz.getStartDate(), quiz.getEndDate(), quiz.isPublished());
// 先刪除 再新增
		List<Integer> list = new ArrayList<>();
		list.add(quizId);
		questionDao.delete(list);
//		questionDao.delete(new ArrayList<>(quizId));
		for (QuestionVo questionVo : questionVoList) {

			try {
				String options = mapper.writeValueAsString(questionVo.getOptionsList());

				questionDao.insert(quizId, questionVo.getQuestionId(), questionVo.getQuestion(), questionVo.getType(),
						questionVo.isRequired(), options);

			} catch (Exception e) {
				return new BasicRes(ReplyMessage.OPTIONS_PARSER_ERROR.getMessage(),
						ReplyMessage.OPTIONS_PARSER_ERROR.getCode());
			}
		}
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}


	/* 前後台 問卷獲取 */
	public GetQuizListRes getList(boolean isFrontEnd) {
		if (isFrontEnd) {// 等同於true
			/*
			 * 返回 前台列表 狀態為: 已發布
			 */
			return new GetQuizListRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(),
					quizDao.getAllPublished());
		}

		return new GetQuizListRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), //
				quizDao.getAll());
	}

	public GetQuestionListRes getQuestionsByQuizId(int quizId) {

		if (quizId <= 0) {
			return new GetQuestionListRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(),
					ReplyMessage.QUIZ_ID_ERROR.getCode());
		}

		List<Question> question = questionDao.getByQuizId(quizId);
		List<QuestionVo> questionVos = new ArrayList<>();
		for (Question item : question) {
// 把 question 自串 options 轉換回 List<String>	
			try {
				List<String> optionsList = mapper.readValue(item.getOptions(), new TypeReference<>() {
				});
				QuestionVo vo = new QuestionVo(quizId, item.getQuestionId(), item.getQuestion(), item.getType(),
						item.isRequired(), optionsList);

				questionVos.add(vo);

			} catch (Exception e) {
				// TODO: handle exception
				return new GetQuestionListRes(ReplyMessage.OPTIONS_PARSER_ERROR.getMessage(),
						ReplyMessage.OPTIONS_PARSER_ERROR.getCode());
			}
		}
		return new GetQuestionListRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), questionVos);
	}

	@Transactional
	public BasicRes deleteQuiz(List<Integer> quizIdList) {

		quizDao.delete(quizIdList);
		questionDao.delete(quizIdList);
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}

	/* 自己寫的 */
	@Transactional
	public BasicRes updatePublishedReq(UpdatePublishedReq req) {
		if (req.getId() <= 0) {
			return new BasicRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(), ReplyMessage.QUIZ_ID_ERROR.getCode());
		}
		int row = quizDao.updatePublishedById(req.getId(), req.isPublished());

		if (row <= 0) {
			return new BasicRes(ReplyMessage.QUIZ_ID_MISMATCH.getMessage(), ReplyMessage.QUIZ_ID_MISMATCH.getCode());
		}
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}

	public GetQuizListRes getQuizById(int id) {
		if (id <= 0) {
			return new GetQuizListRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(), ReplyMessage.QUIZ_ID_ERROR.getCode());
		}
		return new GetQuizListRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), //
				quizDao.getById(id));

}

}
