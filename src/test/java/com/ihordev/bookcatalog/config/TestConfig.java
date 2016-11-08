package com.ihordev.bookcatalog.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ihordev.bookcatalog.dao.impl.DAOmarker; 

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/test-dbconfig.properties")
@ComponentScan(basePackageClasses = {DAOmarker.class})
public class TestConfig
{
	
	@Autowired
	private Environment env;
	
    @Bean
    public DataSource dataSource() {
    	JdbcDataSource ds = new JdbcDataSource();
		ds.setURL(env.getProperty("url"));
		ds.setUser(env.getProperty("user"));
		ds.setPassword(env.getProperty("password"));
		
        return ds;
    }
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[] { env.getProperty("packagesToScan") });
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		
		// ----------------------------------------------
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		
		sfb.setHibernateProperties(props);
		
		return sfb;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

}
