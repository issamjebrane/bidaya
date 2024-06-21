package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
