package com.ihordev.bookcatalog.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.ihordev.bookcatalog.dao.AuthorDAO;
import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.domain.Book;

@Repository
public class AuthorDAOImpl extends GenericDAOImpl<Author, Long> implements AuthorDAO
{

    public AuthorDAOImpl()
    {
        super(Author.class);
    }

    @Override
    public Author findByIdWithBooks(Long id)
    {
        Author author = findById(id);
        Hibernate.initialize(author.getBooks());

        return author;
    }

    public void deleteById(long authorId)
    {
        Author author = findByIdWithBooks(authorId);
        delete(author);
    }

    @Override
    public void delete(Author author)
    {
        if (Hibernate.isInitialized(author.getBooks()) == false)
        {
            author = findByIdWithBooks(author.getId());
        }

        for (Book book : author.getBooks())
        {
            book.getAuthors().remove(author);
            if (book.getAuthors().isEmpty())
            {
                getCurrentSession().delete(book);
            } else
            {
                getCurrentSession().update(book);
            }
        }

        super.delete(author);
    }
}
