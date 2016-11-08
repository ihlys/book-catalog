package com.ihordev.bookcatalog.dao.impl;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ihordev.bookcatalog.config.TestConfig;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class FactorySessionTest
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void shouldLoadSessionFactory()
	{
		Assert.assertNotNull(sessionFactory);
	}
}
