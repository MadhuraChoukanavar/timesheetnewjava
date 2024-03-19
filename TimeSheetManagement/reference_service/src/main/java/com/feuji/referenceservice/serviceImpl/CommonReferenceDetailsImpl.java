package com.feuji.referenceservice.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.referenceservice.bean.CommonReferenceDetailsBean;
import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.bean.ReferenceDetailsBean;
import com.feuji.referenceservice.entity.CommonReferenceDetailsEntity;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceDetailsImpl implements CommonReferenceDetails {

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<ReferenceDetailsBean> getDetailsByTypeId(String typeName) {
		log.info("result"+typeName);
		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeName(typeName);
		log.info("result"+detailsByTypeName);
		
		 List<ReferenceDetailsBean> list = new ArrayList<ReferenceDetailsBean>();
		 
		
		for(String item:detailsByTypeName)
		{
			ReferenceDetailsBean bean = new ReferenceDetailsBean();
			String[] split = item.split(",");
			bean.setReferenceDetailValue(split[0]);
			bean.setReferenceDetailId(Integer.parseInt(split[1]));
			list.add(bean);
		}
		return list;
	}

	@Override
	public CommonReferenceDetailsBean getReferenceById(Integer id) {
	CommonReferenceDetailsEntity commonReferenceDetailsEntity = commonReferenceDetailsRepo.findById(id).orElseThrow();
	
	return modelMapper.map(commonReferenceDetailsEntity, CommonReferenceDetailsBean.class);
	}
}
