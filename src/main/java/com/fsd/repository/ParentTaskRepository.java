package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.ParentTask;

@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTask, Long> {
	
	@Query("select pt FROM ParentTask pt WHERE pt.parenttaskname like %?1%")
    List<ParentTask> findByParentTaskName(String parenttaskname);
}
