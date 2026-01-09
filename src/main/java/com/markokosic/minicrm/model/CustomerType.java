package com.markokosic.minicrm.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="customer_types")
public class CustomerType {
	@Id
	@Column(name="id")
	private Long id;

	@Column(nullable = false, unique = true, name="code")
	private String code;
}
