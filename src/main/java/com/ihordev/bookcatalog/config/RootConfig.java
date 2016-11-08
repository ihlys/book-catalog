package com.ihordev.bookcatalog.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ihordev.bookcatalog.dao.impl.DAOmarker;
import com.ihordev.bookcatalog.service.impl.ServiceMarker;
import com.ihordev.bookcatalog.util.SiteNavigation;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/app-dbconfig.properties")
@ComponentScan(basePackageClasses = {DAOmarker.class, ServiceMarker.class})
public class RootConfig
{
	
	@Autowired
    private Environment env;

	
	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		lookup.setResourceRef(true);
		DataSource dataSource = lookup.getDataSource("jdbc/OracleDB");
		
		return dataSource;
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
	
    @Bean
    public MessageSource messageSource() { 
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/properties/messages");
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    } 
    
    @Bean 
    public SiteNavigation siteNavigation(MessageSource messageSource)
    {
    	SiteNavigation siteNavigation;
		
		DocumentBuilder dBuilder;
    	
		try 
		{		
			InputStream in = new ClassPathResource("/site-navigation.xml").getInputStream();
			
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(in);

			doc.getDocumentElement().normalize();
			
			siteNavigation = SiteNavigation.buildFrom(doc, messageSource);
			
		} catch (IOException | ParserConfigurationException | SAXException e) {
			siteNavigation = SiteNavigation.buildEmpty();
		}
		
    	return siteNavigation;
    }
}
