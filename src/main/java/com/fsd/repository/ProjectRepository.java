package com.fsd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsd.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query("select p FROM Project p WHERE p.projectname like %?1%")
    List<Project> findByProjectName(String projectname);
}
