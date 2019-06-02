package com.pmt.videoscripting.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	@Id
    @GeneratedValue
	private int id;
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	private List<Media> mediafiles = new ArrayList<Media>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Course(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Course() {
		super();
	}
}