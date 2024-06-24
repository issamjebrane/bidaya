package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findByTitle(String title);
}
