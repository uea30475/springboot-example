package com.pmt.addfriend.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="user_id",length = 32)
	private String id;
	@Column(name = "first_name", length = 32)
	@JsonIgnore
	private String firstName;
	@Column(name = "last_name", length = 32)
	@JsonIgnore
	private String lastName;
	@Column(length = 32)
	@JsonIgnore
	private String username;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	@JsonIgnore
	private Set<User> friends = new HashSet<User>();
	@ManyToMany(mappedBy = "friends",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"friends","befriends"})
	private Set<User> befriends = new HashSet<User>();
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "user_friend_request", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	@JsonIgnore
	private Set<User> friend_requests = new HashSet<User>();
	@ManyToMany(mappedBy = "friend_requests",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"friend_requests","befriend_requests"})
	private Set<User> befriend_requests = new HashSet<User>();
	
	@Transient
    public String getFullName() {
        return firstName + " " + lastName + " ";
    }
	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<User> getBefriends() {
		return befriends;
	}

	public void setBefriends(Set<User> befriends) {
		this.befriends = befriends;
	}

	@Column(length = 255)
	@JsonIgnore
	private String password;

	public User() {
		this.id = UUID.randomUUID().toString().substring(32).toUpperCase();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
