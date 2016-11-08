package com.ihordev.bookcatalog.service;

import java.util.List;

import com.ihordev.bookcatalog.domain.Author;

public interface AuthorService
{
	
	Author findById(Long id);

	Author findByIdWithBooks(Long id);
	
	List<Author> findAllAuthors();

	void addAuthor(Author author);
	
	void updateAuthor(Author author);

	void deleteAuthorById(long id);
	
}
