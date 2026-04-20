package com.example.quiz_1150119.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1150119.enity.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
	@Modifying
	@Transactional
	@Query(value = "insert  into quiz (title,description,start_date,end_date,published) "
			+ " values (?1,?2,?3,?4,?5)", nativeQuery = true)
	public void insert(String title,String description , LocalDate startDate,LocalDate enDate,boolean published);

	@Query(value = "select Max(id) from quiz ", nativeQuery = true)
	public int getMaxId();
}
