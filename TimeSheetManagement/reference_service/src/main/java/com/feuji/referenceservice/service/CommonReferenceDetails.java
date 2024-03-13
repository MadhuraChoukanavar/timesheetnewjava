package com.feuji.referenceservice.service;

import java.util.List;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;


public interface CommonReferenceDetails {
//	public List<String> getbyreferenceType(CommonReferenceTypeBean referenceTypeId);

	public List<ReferenceDetailsBean> getDetailsByTypeId(String typeName);
	public CommonReferenceDetailsBean getReferenceById(Integer id);
}
