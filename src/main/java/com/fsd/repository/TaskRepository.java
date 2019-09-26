package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("select t FROM Task t WHERE t.taskname like %?1%")
    List<Task> findByTaskName(String taskname);
	
	@Query("select t FROM Task t WHERE t.projectid = ?1 order by ?2")
    List<Task> sortProjectTask(long projectid, String sort_by);
}
