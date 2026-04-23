package com.example.quiz_1150119.dao;

import java.time.LocalDate;
import java.util.List;

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
	public void insert(String title, String description, //
			LocalDate startDate, LocalDate enDate, boolean published);

	@Query(value = "select Max(id) from quiz ", nativeQuery = true)
	public int getMaxId();

	@Modifying
	@Transactional
	@Query(value = "update quiz set title=?2,description=?3,"
			+ " start_date=?4,end_date=?5,published=?6 where id =?1 ", nativeQuery = true)
	public int update(int id, String title, String description, LocalDate startDate, LocalDate enDate,
			boolean published);

	/* 後台 */
	@Query(value = "select * from quiz ", nativeQuery = true)
	public List<Quiz> getAll();

	/* 前台問眷 */
	@Query(value = "select * from quiz where published = 1", nativeQuery = true)
	public List<Quiz> getAllPublished();

	@Modifying
	@Transactional
	@Query(value = "delete from quiz where id in (?) ", nativeQuery = true)
	public void delete(List<Integer> quizIds);

	@Modifying
	@Transactional
	@Query(value = "update quiz set published=?2 where id =?1 ", nativeQuery = true)
	public int updatePublishedById(int id, boolean publised);

	@Query(value = "select * from quiz where id =? ", nativeQuery = true)
	public List<Quiz> getById(int id);

	@Query(value = "select * from quiz where id =?1 and start_date<=?2 and end_date>=?2 "//
			+ " and published=1 ", nativeQuery = true)
	public Quiz getPublishedQuizBetween(int id, LocalDate today);

	@Query(value = "select * from quiz where id =?1 and start_date<=?2 "
			+ " and published=1 ", nativeQuery = true)
	public Quiz getPublishedQuizAfter(int id, LocalDate today);

}
