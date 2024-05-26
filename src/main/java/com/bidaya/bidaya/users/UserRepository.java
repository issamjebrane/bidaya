package com.bidaya.bidaya.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String Email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.email = :email")
    Optional<User> findByEmailWithProjects(@Param("email") String email);
}
