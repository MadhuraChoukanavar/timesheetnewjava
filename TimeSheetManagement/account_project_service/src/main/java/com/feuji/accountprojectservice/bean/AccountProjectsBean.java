package com.feuji.accountprojectservice.bean;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AccountProjectsBean {

	private Integer accountProjectId;

	private String projectPId;

	private String projectName;

	private Integer accountId;

	private int priority;

	private int projectManagerId;

	private double noOfBillingHours;

	private Timestamp plannedStartDate;

	private Timestamp plannedEndDate;

	private Timestamp actualStartDate;

	private Timestamp actualEndDate;

	private int projectStatus;

	private Boolean isActive;

	private Boolean isRed;

	private Boolean isDeleted;

	private String uuid;

	private String createdBy;

	private Timestamp createdOn;

	private String modifiedBy;

	private Timestamp modifiedOn;

}
