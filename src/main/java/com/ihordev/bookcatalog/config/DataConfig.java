package com.ihordev.bookcatalog.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ihordev.bookcatalog.dao.impl.DAOmarker;
import com.ihordev.bookcatalog.service.impl.ServiceMarker;

@Profile({"production", "db-test"})
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:properties/app-dbconfig.properties")
@ComponentScan(basePackageClasses = { DAOmarker.class, ServiceMarker.class })
public class DataConfig
{

    @Autowired
    private Environment env;

    @Bean
    @Profile("production")
    public DataSource dataSource()
    {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        DataSource dataSource = lookup.getDataSource("jdbc/OracleDB");

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource)
    {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[] { env.getProperty("packagesToScan") });

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.setProperty("hibernate.default_schema", env.getProperty("hibernate.default_schema"));
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        sfb.setHibernateProperties(props);

        return sfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/properties/i18n/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

}

