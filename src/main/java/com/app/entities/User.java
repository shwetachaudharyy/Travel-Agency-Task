package com.app.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = "password")
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Invalid Email format")
	private String email;

	@Column(name = "password", nullable = false)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message = "Invalid Password!")
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "first_name", nullable = false)
	@Length(min = 2, max = 20, message = "Invalid length of the first name")
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@Length(min = 2, max = 20, message = "Invalid length of the last name")
	private String lastName;

	@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	private Passenger passenger;
}