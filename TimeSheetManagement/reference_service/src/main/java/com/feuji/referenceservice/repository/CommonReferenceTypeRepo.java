package com.feuji.referenceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.referenceservice.dto.ReferenceDto;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceTypeRepo extends JpaRepository<CommonReferenceTypeEntity,Integer>{

	@Query(value = "select reference_type_id from common_reference_type where reference_type_name=?",nativeQuery = true)
	public CommonReferenceTypeEntity getByTypeName(String typeName);
	
	@Query("SELECT new com.feuji.referenceservice.dto.ReferenceDto(rd.referenceTypeId, rd.referenceTypeName) " +
            "FROM CommonReferenceTypeEntity rd")
    List<ReferenceDto> findAllReferences();
}
