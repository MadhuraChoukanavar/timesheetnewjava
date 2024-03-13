package com.feuji.accountprojectservice.serviceimpl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;
import com.feuji.accountprojectservice.exception.AccountProjectNotFoundException;
import com.feuji.accountprojectservice.exception.UUIDNotFoundException;
import com.feuji.accountprojectservice.repository.AccountProjectsRepo;
import com.feuji.accountprojectservice.service.AccountProjectsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountProjectsServiceImpl implements AccountProjectsService{
	private static Logger log = LoggerFactory.getLogger(AccountProjectsServiceImpl.class);
	
	@Autowired
	AccountProjectsRepo  accountProjectsRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	

	@Override
	public AccountProjectsEntity save(AccountProjectsBean accountProjectsBean) {
		//AccountProjectsEntity accountProjectsEntity = beanToEntity(accountProjectsBean);

		AccountProjectsEntity	accountProjectsEntity = modelMapper.map(accountProjectsBean, AccountProjectsEntity.class);
		log.info("saving account project entity "+accountProjectsEntity);
		accountProjectsEntity = accountProjectsRepo.save(accountProjectsEntity);
		 return accountProjectsEntity;
	}


//
//	@Override
//	public AccountProjectsEntity getAccountProject(String id) {
//	    try {
//	        Optional<AccountProjectsEntity> optionalAccountProject = Optional.ofNullable(accountProjectsRepo.findaccountProjectByUuid(id));
//	        if (optionalAccountProject.isPresent()) {
//	            return optionalAccountProject.get();
//	        } else {
//	            throw new AccountProjectNotFoundException("Account project with UUID " + id + " not found");
//	        }
//	    } catch (Exception e) {
//	        log.error("Error while fetching account project: {}", e.getMessage(), e);
//	        return null;
//	    }
//	}
	
	@Override
	public AccountProjectsBean findByUuid(String uuid) {
	    AccountProjectsEntity accountProjectEntity = accountProjectsRepo.findByUuid(uuid)
	            .orElseThrow(() -> new IllegalArgumentException("Project not found with id-" + uuid));

	    return modelMapper.map(accountProjectEntity, AccountProjectsBean.class);
	}


	@Override
	public AccountProjectsBean updateAccountProject(AccountProjectsBean accountProjectsBean) {
	    if (accountProjectsBean == null) {
	        throw new IllegalArgumentException("Account project bean object is null");
	    }
	    try {
	        AccountProjectsEntity existingProjectEntity = accountProjectsRepo.findByUuid(accountProjectsBean.getUuid())
	                .orElseThrow(() -> new IllegalArgumentException("Project not found with UUID-" + accountProjectsBean.getUuid()));

//	        // Ensure UUID remains unchanged
//	        accountProjectsBean.setUuid(existingProjectEntity.getUuid());
	        
	        existingProjectEntity.setProjectPId(accountProjectsBean.getProjectPId());

	        modelMapper.map(accountProjectsBean, existingProjectEntity);
	        existingProjectEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
	        AccountProjectsEntity savedEntity = accountProjectsRepo.save(existingProjectEntity);
	        return modelMapper.map(savedEntity, AccountProjectsBean.class);
	    } catch (IllegalArgumentException e) {
	        throw e; // Rethrow the IllegalArgumentException
	    }
	}
	
//	@Override
//	public AccountProjectsBean updateAccountProject(AccountProjectsBean accountProjectsBean) {
//	    if (accountProjectsBean == null) {
//	        throw new IllegalArgumentException("Account project bean object is null");
//	    }
//	    try {
//	        Optional<AccountProjectsEntity> optionalEntity = accountProjectsRepo.findByUuid(accountProjectsBean.getUuid());
//	        if (optionalEntity.isPresent()) {
//	            AccountProjectsEntity existingProjectEntity = optionalEntity.get();
//	            
//	            accountProjectsBean.setUuid(existingProjectEntity.getUuid());
//	  
//	            existingProjectEntity.setProjectName(accountProjectsBean.getProjectName());
//	            existingProjectEntity.setPriority(accountProjectsBean.getPriority());
//	            existingProjectEntity.setProjectStatus(accountProjectsBean.getProjectStatus());
//
//	            if (existingProjectEntity.getModifiedOn() == null) {
//	                existingProjectEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
//	            }
//
//	            AccountProjectsEntity savedEntity = accountProjectsRepo.save(existingProjectEntity);
//	            return modelMapper.map(savedEntity, AccountProjectsBean.class);
//	        } else {
//	            throw new UUIDNotFoundException("Project not found with UUID-" + accountProjectsBean.getUuid());
//	        }
//	    } catch (IllegalArgumentException e) {
//	        throw e; 
//	    }
//	}
	    
	    
	 


	@Override
	public List<AccountProjectsBean> getAllAccountProjects() {
	    List<AccountProjectsEntity> accountProjectsEntity = accountProjectsRepo.findAll();
	    
	    return accountProjectsEntity.stream()
	            .map(entity -> modelMapper.map(entity, AccountProjectsBean.class))
	            .collect(Collectors.toList());
	}


	@Override
	public AccountProjectsBean getAccountProjectBean(Integer id) {
		AccountProjectsEntity accountProjectsEntity = accountProjectsRepo.findById(id).orElseThrow();
		return modelMapper.map(accountProjectsEntity, AccountProjectsBean.class);
	}


}


	
//	// conversion entity to bean and visa versa
//		public AccountProjectsBean entityToBean(AccountProjectsEntity entity) {
//			AccountProjectsBean accountProjectsBean = new AccountProjectsBean();
//
//		
//			accountProjectsBean.setAccountProjectId(entity.getAccountProjectId());
//			accountProjectsBean.setAccountId(entity.getAccountId());
//			accountProjectsBean.setActualEndDate(entity.getActualEndDate());
//			accountProjectsBean.setActualStartDate(entity.getActualStartDate());
//			accountProjectsBean.setCreatedBy(entity.getCreatedBy());
//			accountProjectsBean.setCreatedOn(entity.getCreatedOn());
//			accountProjectsBean.setIsActive(entity.getIsActive());
//			accountProjectsBean.setIsDeleted(entity.getIsDeleted());
//			accountProjectsBean.setIsRed(entity.getIsRed());
//			accountProjectsBean.setModifiedBy(entity.getModifiedBy());
//			accountProjectsBean.setModifiedOn(entity.getModifiedOn());
//			accountProjectsBean.setUuid(entity.getUuid());
//			accountProjectsBean.setStatus(entity.getStatus());
//			accountProjectsBean.setProjectPId(entity.getProjectPId());
//			accountProjectsBean.setProjectManagerId(entity.getProjectManagerId());
//			accountProjectsBean.setPriority(entity.getPriority());
//			accountProjectsBean.setPlannedStartDate(entity.getPlannedStartDate());
//			accountProjectsBean.setPlannedEndDate(entity.getPlannedEndDate());
//			accountProjectsBean.setNoOfBillingHours(entity.getNoOfBillingHours());
//			accountProjectsBean.setName(entity.getName());
//			return accountProjectsBean;
//		}
//
//		public AccountProjectsEntity beanToEntity(AccountProjectsBean accountProjectsBean) {
//		    AccountProjectsEntity accountProjectsEntity = new AccountProjectsEntity();
//
//		    accountProjectsEntity.setAccountProjectId(accountProjectsBean.getAccountProjectId());
//		    accountProjectsEntity.setAccountId(accountProjectsBean.getAccountId());
//		    accountProjectsEntity.setActualEndDate(accountProjectsBean.getActualEndDate());
//		    accountProjectsEntity.setActualStartDate(accountProjectsBean.getActualStartDate());
//		    accountProjectsEntity.setCreatedBy(accountProjectsBean.getCreatedBy());
//		    accountProjectsEntity.setCreatedOn(accountProjectsBean.getCreatedOn());
//		    accountProjectsEntity.setIsActive(accountProjectsBean.getIsActive());
//		    accountProjectsEntity.setIsDeleted(accountProjectsBean.getIsDeleted());
//		    accountProjectsEntity.setIsRed(accountProjectsBean.getIsRed());
//		    accountProjectsEntity.setModifiedBy(accountProjectsBean.getModifiedBy());
//		    accountProjectsEntity.setModifiedOn(accountProjectsBean.getModifiedOn());
//		    accountProjectsEntity.setUuid(accountProjectsBean.getUuid());
//		    accountProjectsEntity.setStatus(accountProjectsBean.getStatus());
//		    accountProjectsEntity.setProjectPId(accountProjectsBean.getProjectPId());
//		    accountProjectsEntity.setProjectManagerId(accountProjectsBean.getProjectManagerId());
//		    accountProjectsEntity.setPriority(accountProjectsBean.getPriority());
//		    accountProjectsEntity.setPlannedStartDate(accountProjectsBean.getPlannedStartDate());
//		    accountProjectsEntity.setPlannedEndDate(accountProjectsBean.getPlannedEndDate());
//		    accountProjectsEntity.setNoOfBillingHours(accountProjectsBean.getNoOfBillingHours());
//		    accountProjectsEntity.setName(accountProjectsBean.getName());
//
//		    return accountProjectsEntity;
//		}
	
	



