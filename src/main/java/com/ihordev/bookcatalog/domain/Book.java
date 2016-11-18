package com.ihordev.bookcatalog.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@NamedQueries({
    @NamedQuery(name = Book.findBooksByTitle, 
                query = "from Book book where lower(book.title) like lower(concat('%', :title, '%'))") 
})
@Entity
@Table(name = "Books")
public class Book
{
    public static final String findBooksByTitle = "Book.findBooksByTitle";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;                           // business key

    @NotBlank
    private String description;

    // property checked in @initBinder in BookController.class
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Size(min = 1)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "BOOK_AUTHOR", 
        joinColumns = @JoinColumn(name = "BOOK_ID"), 
        inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Set<Author> authors = new HashSet<>();  // business key

    public Book()
    {
        // TODO Auto-generated constructor stub
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getPublicationDate()
    {
        return (publicationDate == null) ? null : new Date(publicationDate.getTime());
    }

    public void setPublicationDate(Date publicationDate)
    {
        this.publicationDate = new Date(publicationDate.getTime());
    }

    public Set<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(Set<Author> authors)
    {
        this.authors = authors;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAuthors() == null) ? 0 : getAuthors().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Book))
            return false;

        Book other = (Book) obj;

        if (getTitle() == null)
        {
            if (other.getTitle() != null)
                return false;
        } else if (!getTitle().equals(other.getTitle()))
            return false;

        if (getAuthors().isEmpty())
        {
            if (!other.getAuthors().isEmpty())
                return false;
        } else if (!getAuthors().equals(other.getAuthors()))
            return false;

        return true;
    }

}
