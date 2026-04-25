package com.example.quiz_1150119.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.quiz_1150119.constants.ReplyMessage;
import com.example.quiz_1150119.constants.Type;
import com.example.quiz_1150119.dao.FillinDao;
import com.example.quiz_1150119.dao.QuestionDao;
import com.example.quiz_1150119.dao.QuizDao;
import com.example.quiz_1150119.dao.UserDao;
import com.example.quiz_1150119.enity.Fillin;
import com.example.quiz_1150119.enity.Question;
import com.example.quiz_1150119.enity.Quiz;
import com.example.quiz_1150119.enity.User;
import com.example.quiz_1150119.request.FillinReq;
import com.example.quiz_1150119.response.BasicRes;
import com.example.quiz_1150119.response.FeedBackRes;
import com.example.quiz_1150119.response.GetUserWriteQuizIdRes;
import com.example.quiz_1150119.response.StatisticsRes;
import com.example.quiz_1150119.vo.AnswersVo;
import com.example.quiz_1150119.vo.FeedBackVo;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class FillinService {
	/* 物件 字串的轉換工具 */
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

	@Transactional
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

		// Lambda 表達式: List 轉成 Map
		// Map<Integer, List<String>> voMap =
		// req.getAnswersVoList().stream().collect(Collectors.toMap(
		// AnswersVo::getQuestionId, // Key: Question ID AnswersVo::getAnswersList //
		// Value: 答案列表 ));

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
				if (!Type.isChoice(question.getType())) {
					continue;
				}
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
					String str = mapper.writeValueAsString(vo.getAnswerList());
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

	public FeedBackRes feedback(int quizId) {
		if (quizId <= 0) {
			return new FeedBackRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(), ReplyMessage.QUIZ_ID_ERROR.getCode());
		}
		List<Fillin> fillinList = fillinDao.getByQuizId(quizId);
		/* 將email 和每個USER 填的問題 的答案 轉成map */
		Map<String, List<AnswersVo>> map = new HashMap<>();
		Map<String, LocalDate> dateMap = new HashMap<>();
		for (Fillin fillin : fillinList) {
			/* answer 的字串 轉成 List<String> */
			try {
				List<AnswersVo> answersVoList = new ArrayList<>();
				List<String> answersList = mapper.readValue(fillin.getAnswers(), new TypeReference<>() {
				});
				AnswersVo vo = new AnswersVo(fillin.getQuestionId(), answersList);
				/*
				 * 查看 map 的 key(email) 是否已存在，若不檢查，迴圈每次新的一run 時， answersVoList 都是新的(空的)，等執行到
				 * map.put() 方法時，因為 map 對相同的 key 值， 其對應的 value 會後蓋前，所以要把已存在的 key 對應的
				 * answersVoList 取出後再把新的 answersVo 加入到裡面
				 */
				if (map.containsKey(fillin.getEmail())) {
					answersVoList = map.get(fillin.getEmail());
				}
				/*
				 * map 中的email 不存在 =>使用上面 的new 出來的answersVoList
				 */
				answersVoList.add(vo);
				map.put(fillin.getEmail(), answersVoList);
				/* 紀錄填寫日期 */
				dateMap.put(fillin.getEmail(), fillin.getFillin_date());
			} catch (Exception e) {
				// TODO: handle exception
				return new FeedBackRes(ReplyMessage.ANSWERS_PARSER_ERROR.getMessage(),
						ReplyMessage.ANSWERS_PARSER_ERROR.getCode());
			}

		}

		/* 檢查是選擇類型，答案跟選項要符合 --> 拿答案來跟選項比對 */
		/*
		 * 1. 先把 Question 中的 options 字串轉換成 List<String>， 因為寫進 DB 的字串值 就是由 List<String>
		 * 轉換來的
		 */

		List<FeedBackVo> feedBackVos = new ArrayList<>();
		for (String email : map.keySet()) {
			User user = userDao.getByEmail(email);
			FeedBackVo vo = new FeedBackVo(user, dateMap.get(email), map.get(email));
			feedBackVos.add(vo);

		}

		return new FeedBackRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), //
				quizId, feedBackVos);

	}

	public StatisticsRes statistics(int quizId) {
		if (quizId <= 0) {
			return new StatisticsRes(ReplyMessage.QUIZ_ID_ERROR.getMessage(), ReplyMessage.QUIZ_ID_ERROR.getCode());
		}
		List<Fillin> fillinList = fillinDao.getByQuizId(quizId);
		/* Map<QuestionId, answersList> */
		Map<Integer, List<String>> map = new HashMap<>();
		for (Fillin fillin : fillinList) {
			try {
				// 1. 先取出原本已有的答案清單，若無則建立新的
				List<String> answersList = map.getOrDefault(fillin.getQuestionId(), new ArrayList<>());

				// 2. 解析資料庫裡的答案字串 (例如將 '["A"]' 轉成 List)
				List<String> currentAnswers = mapper.readValue(fillin.getAnswers(), new TypeReference<List<String>>() {
				});

				// 3. 使用 addAll 將新答案併入總清單
				answersList.addAll(currentAnswers);

				// 4. 重要：一定要 put 回 Map
				map.put(fillin.getQuestionId(), answersList);
			} catch (Exception e) {
				e.printStackTrace();
				return new StatisticsRes(ReplyMessage.ANSWERS_PARSER_ERROR.getMessage(),
						ReplyMessage.ANSWERS_PARSER_ERROR.getCode() //
				);
			}
		}
		List<AnswersVo> answersVoList = new ArrayList<>();
		for (Integer questionId : map.keySet()) {
			answersVoList.add(new AnswersVo(questionId, map.get(questionId)));
		}
		return new StatisticsRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), //
				answersVoList);
	}

	/* 取得登入後的user email 回傳user 寫過的quiz ID 去前端比對並把寫過的變成 查看 有誰寫過 跟看他的 答案 */
	public GetUserWriteQuizIdRes getUserWriteQuizId(String email) {
		if (!StringUtils.hasText(email)) {
			return new GetUserWriteQuizIdRes(ReplyMessage.EMAIL_NOT_FOUND.getMessage(),
					ReplyMessage.EMAIL_NOT_FOUND.getCode());
		}
		List<Integer> quizIdList = fillinDao.getByEmai(email);

		return new GetUserWriteQuizIdRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode(), quizIdList);
	}
}
