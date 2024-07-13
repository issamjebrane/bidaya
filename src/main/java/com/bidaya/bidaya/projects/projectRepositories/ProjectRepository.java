package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Category;
import com.bidaya.bidaya.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findByTitle(String title);

    List<Project> findByCategory(Category category);

    List<Project> findAllByOrderByCreationDateAsc();
    List<Project> findAllByOrderByCreationDateDesc();

    List<Project> findAllByOrderByGoalAsc();

    List<Project> findAllByOrderByGoalDesc();
    List<Project> findByTitleContainingIgnoreCase(String title);
}
