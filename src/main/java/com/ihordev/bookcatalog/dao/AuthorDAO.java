package com.ihordev.bookcatalog.dao;

import com.ihordev.bookcatalog.domain.Author;

public interface AuthorDAO extends GenericDAO<Author, Long>
{
	Author findByIdWithBooks(Long id);
	
	void deleteById(long authorId);
}
