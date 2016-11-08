package com.ihordev.bookcatalog.dao;

import java.util.List;

import com.ihordev.bookcatalog.domain.Book;

public interface BookDAO extends GenericDAO<Book, Long>
{
	List<Book> findBooksByTitle(String title);
	
	void deleteById(long bookId);
}
