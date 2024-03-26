package com.feuji.employeeservice.dto;

import java.sql.Timestamp;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UpadteEmployeeDto {
	private Integer employeeId;
	private String employeeCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String image;
	private String designation;
	private String email;
	private Integer genderId;
	private String gender;
	private Timestamp dateOfJoining;
	private Integer empTypeId;
	private String empType;
	private String managerFirstName;
	private String managerLastNamee;
	private String managerMiddleName;
	private Integer managerId;
	private Integer deliveryUnitId;
	private String deliveryUnit;
	private Integer businessUnitId;
	private String businessUnit;
	private Integer statusId;
	private String status;
	private String uuid;
	 private Boolean isDeleted;
	public UpadteEmployeeDto(Integer employeeId, String employeeCode, String firstName, String middleName,
			String lastName, String image, String designation, String email, Integer genderId, String gender,
			Timestamp dateOfJoining, Integer empTypeId, String empType, String managerFirstName,
			String managerLastNamee, String managerMiddleName, Integer managerId, Integer deliveryUnitId,
			String deliveryUnit, Integer businessUnitId, String businessUnit, Integer statusId, String status,
			String uuid, Boolean isDeleted) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.image = image;
		this.designation = designation;
		this.email = email;
		this.genderId = genderId;
		this.gender = gender;
		this.dateOfJoining = dateOfJoining;
		this.empTypeId = empTypeId;
		this.empType = empType;
		this.managerFirstName = managerFirstName;
		this.managerLastNamee = managerLastNamee;
		this.managerMiddleName = managerMiddleName;
		this.managerId = managerId;
		this.deliveryUnitId = deliveryUnitId;
		this.deliveryUnit = deliveryUnit;
		this.businessUnitId = businessUnitId;
		this.businessUnit = businessUnit;
		this.statusId = statusId;
		this.status = status;
		this.uuid = uuid;
		this.isDeleted = isDeleted;
	}
	

}



