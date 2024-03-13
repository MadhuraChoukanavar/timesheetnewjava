package com.feuji.employeeskillservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.feuji.employeeskillservice"})
@EnableTransactionManagement
public class JPAConfig {

}
