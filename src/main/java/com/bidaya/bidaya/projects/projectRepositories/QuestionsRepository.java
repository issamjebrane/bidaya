package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Questions;
import com.bidaya.bidaya.projects.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    List<Optional<Questions>> findByStoryId(Long id);
}
