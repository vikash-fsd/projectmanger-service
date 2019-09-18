package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query("FROM Project WHERE project_name = ?1")
    List<Project> findByProjectName(String project_name);
}