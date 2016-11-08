package com.ihordev.bookcatalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihordev.bookcatalog.dao.BookDAO;
import com.ihordev.bookcatalog.domain.Book;
import com.ihordev.bookcatalog.service.BookService;

@Service
public class BookServiceImpl implements BookService
{

	@Autowired
	private BookDAO bookDAO; 
	
	@Override
	@Transactional
	public void addBook(Book book)
	{
		bookDAO.add(book);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Book findById(Long id)
	{
		return bookDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> findAllBooks()
	{
		return bookDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> findBooksByTitle(String title)
	{
		return bookDAO.findBooksByTitle(title);
	}
	
	@Override
	@Transactional
	public void updateBook(Book book)
	{
		bookDAO.update(book);
	}

	@Override
	@Transactional
	public void deleteBookById(long bookId)
	{
		bookDAO.deleteById(bookId);
	}

}
