package com.knoldus.knolbucks.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Document
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
@Setter
public class User {

    @Id
	ObjectId _id;

    @Setter
    @NotEmpty(message = "Please check name")
    @Size(min=5, max = 50, message = "Name should be atleast 5 characters and maximum 50.")
	String name;

    @NotNull
    @NotEmpty(message = "Please check Email")
    @Email(message = "Email Format is wrong.")
	String email;

	@JsonFormat(pattern = "yyyy-MM-dd")
	Date dateOfBirth;

	@NotEmpty(message = "Please check role (Admin, Employee, Finance, HR")
	RoleEnum role;

	Boolean active;

	String employeeId;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
