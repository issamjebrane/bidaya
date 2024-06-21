package com.bidaya.bidaya.projects.projectRepositories;

import com.bidaya.bidaya.projects.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
}
