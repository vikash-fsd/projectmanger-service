package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	@Query("FROM Users WHERE firstname = ?1")
    List<Users> findByFirstName(String firstname);
}
