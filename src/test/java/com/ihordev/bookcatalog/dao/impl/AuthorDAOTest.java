package com.ihordev.bookcatalog.dao.impl;

import java.io.InputStream;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.ihordev.bookcatalog.dao.AuthorDAO;
import com.ihordev.bookcatalog.dao.BookDAO;
import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.domain.Book;

public class AuthorDAOTest extends DAOTestCase
{

	@Autowired
	private AuthorDAO authorDAO;
	
	@Override
	protected IDataSet getDataSet() throws Exception
	{
		ClassPathResource cpr = new ClassPathResource("testdata/authordaotests-data.xml");
		InputStream is = cpr.getInputStream();
		return new FlatXmlDataSetBuilder().build(is);
	}

	
	@Test
	public void shouldAddNewAuthor()
	{
		Author author = new Author();
		author.setForename("Added author forename");
		author.setSurname("Added author surname");
		
		authorDAO.add(author);
		
		assertNotNull(author.getId());
	}
	
	@Test
	public void shoudFindAuthorById()
	{
		Author author = authorDAO.findById(1L);
		
		assertNotNull(author);
		assertEquals("Author one forename", author.getForename());
		assertEquals("Author one surname", author.getSurname());
	}
	
	@Test
	public void shoudFindAllAuthors()
	{
		List<Author> authors = authorDAO.findAll();
		
		assertEquals(3, authors.size());
	}
	
	@Test
	public void shouldLoadAuthorWithHisBooks()
	{
		Author author = authorDAO.findByIdWithBooks(3L);
		
		assertNotNull(author);
		
		Book book1 = new Book();
		book1.setTitle("Book four");
		book1.getAuthors().add(author);
		
		Book book2 = new Book();
		book2.setTitle("Book five");
		book2.getAuthors().add(author);
		
		assertTrue(author.getBooks().contains(book1));
		assertTrue(author.getBooks().contains(book2));
	}
	
	@Autowired
	private BookDAO bookDAO;
	
	@Test
	public void shouldDeleteAuthorAndDeleteOnlyHisBooks()
	{
		Author author = authorDAO.findById(1L);
		
		authorDAO.delete(author);
				
		Book book1 = bookDAO.findById(1L);
		Book book2 = bookDAO.findById(2L);
		Book book3 = bookDAO.findById(3L);
		
		assertNotNull(book1);
		assertNull(book2);
		assertNull(book3);
	}
	
	@Test
	public void shouldDeleteAuthorAndDeleteOnlyHisBooks2()
	{
		Author author = authorDAO.findByIdWithBooks(1L);
		
		authorDAO.delete(author);
				
		Book book1 = bookDAO.findById(1L);
		Book book2 = bookDAO.findById(2L);
		Book book3 = bookDAO.findById(3L);
		
		assertNotNull(book1);
		assertNull(book2);
		assertNull(book3);
	}
	
}
