package com.example.quiz_1150119.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz_1150119.enity.Fillin;
import com.example.quiz_1150119.enity.FillinId;

@Repository
public interface FillinDao extends JpaRepository<Fillin, FillinId> {

}
