package com.ihordev.bookcatalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihordev.bookcatalog.dao.AuthorDAO;
import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService
{
	
	@Autowired
	private AuthorDAO authorDAO;

	@Override
	@Transactional
	public void addAuthor(Author author)
	{
		authorDAO.add(author);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Author findByIdWithBooks(Long id)
	{
		return authorDAO.findByIdWithBooks(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Author findById(Long id)
	{
		return authorDAO.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Author> findAllAuthors()
	{
		return authorDAO.findAll();
	}

	@Override
	@Transactional
	public void deleteAuthorById(long authorId)
	{
		authorDAO.deleteById(authorId);
	}

	@Override
	@Transactional
	public void updateAuthor(Author author)
	{
		authorDAO.update(author);
	}

}
