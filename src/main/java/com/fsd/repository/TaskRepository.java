package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("FROM Task WHERE taskname = ?1")
    List<Task> findByTaskName(String taskname);
}
