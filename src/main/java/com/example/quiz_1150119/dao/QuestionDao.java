package com.example.quiz_1150119.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1150119.enity.Question;
import com.example.quiz_1150119.enity.QuestionId;

@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionId> {
	@Modifying
	@Transactional
	@Query(value = "insert  into question (quiz_id,question_id,question,type,required,options) "
			+ " values (?1,?2,?3,?4,?5,?6)", nativeQuery = true)
	public void insert(int quiz_id, int question_id, String question, String type, boolean required, String options);



	@Query(value = "select * from question where quiz_id=? ", nativeQuery = true)
	public List<Question> getByQuizId(int quizId);



	@Modifying
	@Transactional
	@Query(value = "delete from question where quiz_id   in(?) ", nativeQuery = true)
	public void delete(List<Integer> quizIds);
}
