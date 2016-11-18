package com.ihordev.bookcatalog.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ihordev.bookcatalog.dao.BookDAO;
import com.ihordev.bookcatalog.domain.Book;

@Repository
public class BookDAOImpl extends GenericDAOImpl<Book, Long> implements BookDAO
{
    public BookDAOImpl()
    {
        super(Book.class);
    }

    @Override
    public List<Book> findBooksByTitle(String title)
    {
        TypedQuery<Book> query = getCurrentSession().createNamedQuery(Book.findBooksByTitle, Book.class);

        query.setParameter("title", title);
        return query.getResultList();
    }

    public void deleteById(long bookId)
    {
        Book book = findById(bookId);

        delete(book);
    }
}
