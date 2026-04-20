package com.example.quiz_1150119.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1150119.enity.Qusetion;
import com.example.quiz_1150119.enity.QusetionId;

@Repository
public interface QusetionDao extends JpaRepository<Qusetion, QusetionId> {
	@Modifying
	@Transactional
	@Query(value = "insert  into question (quiz_id,question_id,question,type,required,options) "
			+ " values (?1,?2,?3,?4,?5,?6)", nativeQuery = true)
	public void insert(int quiz_id, int question_id, String question, String type, boolean required, String options);

}
