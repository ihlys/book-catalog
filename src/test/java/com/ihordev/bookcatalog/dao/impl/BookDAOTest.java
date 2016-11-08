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


public class BookDAOTest extends DAOTestCase
{
	
	@Autowired
	private BookDAO bookDAO;
	
	@Override
	protected IDataSet getDataSet() throws Exception
	{
		ClassPathResource cpr = new ClassPathResource("testdata/bookdaotests-data.xml");
		InputStream is = cpr.getInputStream();
		return new FlatXmlDataSetBuilder().build(is);
	}
	
	@Test
	public void shouldFindBookByTitle()
	{
		List<Book> books = bookDAO.findBooksByTitle("extra");
		
		assertEquals(2, books.size());
	}

	@Test
	public void shouldFindAllBooks()
	{
		List<Book> books = bookDAO.findAll();
		
		assertEquals(7, books.size());
	}
	
	@Test
	public void shouldFindBookById()
	{
		Book book = bookDAO.findById(4L);
		
		assertNotNull(book);
		assertEquals("Book four", book.getTitle());
	}
	
	@Test
	public void shouldFindBooksAuthors()
	{
		Book book = bookDAO.findById(6L);

		assertEquals(2, book.getAuthors().size());
		
		Author author1 = new Author();
		author1.setForename("Author two forename");
		author1.setSurname("Author two surname");
		
		Author author2 = new Author();
		author2.setForename("Author three forename");
		author2.setSurname("Author three surname");
		
		assertTrue(book.getAuthors().contains(author1));
		assertTrue(book.getAuthors().contains(author2));
	}
	
	@Autowired
	private AuthorDAO authorDAO;
	
	@Test
	public void shouldUpdateBookWithNewInfo()
	{
		Book book = bookDAO.findById(6L);
		book.setTitle("New title");
		book.setDescription("New description");
		
		bookDAO.update(book);
		
		book = bookDAO.findById(6L);
		
		assertEquals("New title", book.getTitle());
		assertEquals("New description", book.getDescription());
	}
	
	@Test
	public void shouldUpdateBookWithNewAuthors()
	{
		Author author1 = authorDAO.findById(5L);
		Author author2 = authorDAO.findById(6L);
		Author author3 = authorDAO.findById(7L);
		
		Book book = bookDAO.findById(7L);
		
		assertTrue(book.getAuthors().size() == 1);
		
		book.getAuthors().add(author1);
		book.getAuthors().add(author2);
		book.getAuthors().add(author3);
		
		bookDAO.update(book);
		
		book = bookDAO.findById(7L);
		
		assertTrue(book.getAuthors().size() == 4);
		
		assertTrue(book.getAuthors().contains(author1));
		assertTrue(book.getAuthors().contains(author2));
		assertTrue(book.getAuthors().contains(author3));
	}
	
	@Test
	public void shouldSaveNewBook()
	{
		Author author1 = authorDAO.findById(5L);
		Author author2 = authorDAO.findById(6L);
		Author author3 = authorDAO.findById(7L);
		
		Book book = new Book();
		book.setTitle("New book");
		book.setDescription("This is a new book");
		
		book.getAuthors().add(author1);
		book.getAuthors().add(author2);
		book.getAuthors().add(author3);
		
		bookDAO.add(book);
		
		book = bookDAO.findBooksByTitle("New book").get(0);
		
		assertTrue(book.getAuthors().size() == 3);
		
		assertTrue(book.getAuthors().contains(author1));
		assertTrue(book.getAuthors().contains(author2));
		assertTrue(book.getAuthors().contains(author3));
	}
	
	@Test
	public void shouldDeleteBook()
	{
		Book book = bookDAO.findById(5L);
		
		assertNotNull(book);
		
		bookDAO.delete(book);
		
		Book deletedBook = bookDAO.findById(5L);
		
		assertNull(deletedBook);

		Author author = authorDAO.findByIdWithBooks(1L);
		
		assertFalse(author.getBooks().contains(book));
	}
}
