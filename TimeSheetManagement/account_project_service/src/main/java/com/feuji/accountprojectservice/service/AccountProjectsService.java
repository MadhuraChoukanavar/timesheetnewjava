package com.feuji.accountprojectservice.service;

import java.util.List;


import com.feuji.accountprojectservice.bean.AccountProjectsBean;
import com.feuji.accountprojectservice.entity.AccountProjectsEntity;

public interface AccountProjectsService {
	
	public AccountProjectsEntity save(AccountProjectsBean accountProjectsBean);
	
	public AccountProjectsBean findByUuid(String uuid);
	
	public AccountProjectsBean updateAccountProject(AccountProjectsBean accountProjectsBean) ;
	
	public List<AccountProjectsBean> getAllAccountProjects();
	
	public AccountProjectsBean getAccountProjectBean(Integer id);

}
