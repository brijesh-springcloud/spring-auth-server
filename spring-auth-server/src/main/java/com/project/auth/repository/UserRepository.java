package com.project.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.auth.entity.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
