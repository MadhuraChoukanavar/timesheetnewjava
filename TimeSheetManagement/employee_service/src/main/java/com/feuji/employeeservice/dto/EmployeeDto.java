package com.feuji.employeeservice.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDto {
	private Integer userEmpId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String image;
	private String designation;
	private String email;
	private Integer gender;
	private Timestamp dateOfJoining;
	private Integer reportingManagerId;
	private Integer employmentType;
	private Integer status;
	private Integer deliveryUnitId;
	private Integer businessUnitId;


}