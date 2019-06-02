package com.pmt.addfriend.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "user_id", length = 32)
  private String id;
  @Column(name = "first_name", length = 32)
  private String firstName;
  @Column(name = "last_name", length = 32)
  private String lastName;
  private String gender;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  @DateTimeFormat(pattern = "yyyy-mm-dd")
  private Date  birthDate;
  private String email;
  
  @Transient
  private String fullName;
  public String getFullName() {
    return firstName + " " + lastName;
  }

  public void setFullName(String fullName) {
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private String phone;


  public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

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




}
