package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Project;
import com.bidaya.bidaya.projects.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Optional<Story> findByProjectId(long project);
}
