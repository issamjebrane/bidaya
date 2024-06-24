package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
    List<Optional<Rewards>> findByProjectId(Long id);
}
