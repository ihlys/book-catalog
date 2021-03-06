package com.ihordev.bookcatalog.dao.impl;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ihordev.bookcatalog.config.TestDataConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataConfig.class)
@TestPropertySource("classpath:properties/test-dbconfig.properties")
@ActiveProfiles("db-test")
public abstract class AbstractDAOTest
{
    @Autowired
    private DataSource dataSource;

    private IDatabaseConnection dbConn;

    public IDatabaseConnection getConnection()
    {
        return dbConn;
    }

    @Before
    public void setUp() throws Exception
    {
        dbConn = new DatabaseDataSourceConnection(dataSource, "PUBLIC");
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
    }

    protected abstract IDataSet getDataSet() throws Exception;

    protected IDataSet getCurrentStateDataSet() throws Exception
    {
        return getConnection().createDataSet();
    }

}