package com.feuji.skillgapservice.service;

import java.sql.Timestamp;
import java.util.List;

import com.feuji.skillgapservice.bean.SkillBean;

public interface SkillService {
   public SkillBean saveSkill(SkillBean skillBean);
   public SkillBean getByUuid(String uuid);
   public SkillBean updateDetails(SkillBean skillBean);
   public List<SkillBean> getAllSkills();

   
   
   
}
