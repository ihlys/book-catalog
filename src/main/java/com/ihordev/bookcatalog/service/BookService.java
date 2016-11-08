package com.ihordev.bookcatalog.service;

import java.util.List;

import com.ihordev.bookcatalog.domain.Book;

public interface BookService
{
	
	Book findById(Long id);
	
	List<Book> findAllBooks();
	
	List<Book> findBooksByTitle(String title);

	void addBook(Book book);

	void updateBook(Book book);
	
	void deleteBookById(long bookId);
}
