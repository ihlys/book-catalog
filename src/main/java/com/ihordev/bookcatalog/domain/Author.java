package com.ihordev.bookcatalog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Authors")
public class Author
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String forename; // business key
    @NotBlank
    private String surname; // business key

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author()
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

    public String getForename()
    {
        return forename;
    }

    public void setForename(String forename)
    {
        this.forename = forename;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public Set<Book> getBooks()
    {
        return books;
    }

    public void setBooks(Set<Book> books)
    {
        this.books = books;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getForename() == null) ? 0 : getForename().hashCode());
        result = prime * result + ((getSurname() == null) ? 0 : getSurname().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Author))
            return false;

        Author other = (Author) obj;
        if (getForename() == null)
        {
            if (other.getForename() != null)
                return false;
        } else if (!getForename().equals(other.getForename()))
            return false;
        if (getSurname() == null)
        {
            if (other.getSurname() != null)
                return false;
        } else if (!getSurname().equals(other.getSurname()))
            return false;
        return true;
    }

}
