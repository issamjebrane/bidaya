package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
