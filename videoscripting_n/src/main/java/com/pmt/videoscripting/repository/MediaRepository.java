package com.pmt.videoscripting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pmt.videoscripting.models.Media;

public interface MediaRepository extends JpaRepository<Media, Integer> {
	@Query("SELECT M FROM Media M JOIN M.course WHERE M.course.name LIKE %:name%")
	List<Media> findByCourseName(@Param(value = "name") String name);
	Media findByCode(String code);
}
