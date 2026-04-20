package com.example.quiz_1150119.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz_1150119.constants.ReplyMessage;
import com.example.quiz_1150119.dao.UserDao;
import com.example.quiz_1150119.response.BasicRes;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public BasicRes addInfo(String email, String name, String phone, int age) {
		if (!StringUtils.hasText(email)) {// 等同於false
			return new BasicRes(ReplyMessage.PARAM_EMAIL_ERROR.getMessage(), //
					ReplyMessage.PARAM_EMAIL_ERROR.getCode());
		}

		if (!StringUtils.hasText(name)) {// 等同於false
			return new BasicRes(ReplyMessage.PARAM_NAME_ERROR.getMessage(), //
					ReplyMessage.PARAM_NAME_ERROR.getCode());
		}

		if (age < 0) {
			return new BasicRes(ReplyMessage.PARAM_AGE_ERROR.getMessage(), //
					ReplyMessage.PARAM_AGE_ERROR.getCode());
		}
		userDao.insert(email, name, phone, age);
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}

	public BasicRes login(String email) {
		if (!StringUtils.hasText(email)) {// 等同於false
			return new BasicRes(ReplyMessage.PARAM_EMAIL_ERROR.getMessage(), //
					ReplyMessage.PARAM_EMAIL_ERROR.getCode());
		}

		if (userDao.selectCount(email) != 1) {
			return new BasicRes(ReplyMessage.EMAIL_NOT_FOUND.getMessage(), //
					ReplyMessage.EMAIL_NOT_FOUND.getCode());
		}
		return new BasicRes(ReplyMessage.SUCCESS.getMessage(), ReplyMessage.SUCCESS.getCode());
	}
}
