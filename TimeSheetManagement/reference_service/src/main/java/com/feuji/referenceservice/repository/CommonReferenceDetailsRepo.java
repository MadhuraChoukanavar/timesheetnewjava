package com.feuji.referenceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.referenceservice.entity.CommonReferenceDetailsEntity;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceDetailsRepo extends JpaRepository<CommonReferenceDetailsEntity, Integer>{
	

	@Query(value ="select reference_details_values,reference_details_id from common_reference_details "
			+ "rd where rd.reference_type_id=(select reference_type_id from common_reference_type"
			+ " where reference_type_name=:typeName)", nativeQuery = true )
	public List<String> getDetailsByTypeName(String typeName);

}
