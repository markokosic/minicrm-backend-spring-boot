package com.markokosic.minicrm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="auth_users")
public class AuthUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name = "tenant_id", nullable = false)
	@NotNull
	private Long tenantId;

	@Column(name="email")
	@Email
	@NotNull
	private String email;

}









