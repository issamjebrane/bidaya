package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
}
