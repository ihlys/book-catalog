package com.ihordev.bookcatalog.web;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.domain.Book;
import com.ihordev.bookcatalog.service.AuthorService;
import com.ihordev.bookcatalog.service.BookService;


@Controller
@RequestMapping("/books")
public class BookController
{
	private BookService bookService;
	private AuthorService authorService;
	
    @Autowired
    public void setBookService(BookService bookService)
	{
		this.bookService = bookService;
	}
    
    @Autowired
    public void setAuthorService(AuthorService authorService)
	{
		this.authorService = authorService;
	}
	
    
	@RequestMapping(method = RequestMethod.GET)
	public String showBooks(@RequestParam(required = false) String bookTitle, Model model)
	{
		List<Book> books;
		if (bookTitle == null) 
		{
			books = bookService.findAllBooks();
		} else
		{
			books = bookService.findBooksByTitle(bookTitle);
		}
		
		model.addAttribute("books", books);
		
		return "booksList";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showBookCreationForm(Model model)
	{
		model.addAttribute("book", new Book());
		model.addAttribute("authorsList", authorService.findAllAuthors());
		
		return "createBook";
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createBook(@Valid Book book, Errors errors, Model model)
	{
		if (errors.hasErrors())
		{
			model.addAttribute(book);
			model.addAttribute("authorsList", authorService.findAllAuthors());
			return "createBook";
		}
		
		bookService.addBook(book);
		
		return "redirect:/books";
	}
	
	
	@RequestMapping(value = "/{bookId}/update", method = RequestMethod.GET)
	public String showBookUpdateForm(@PathVariable long bookId, Model model)
	{	
		model.addAttribute("book", bookService.findById(bookId));
		model.addAttribute("authorsList", authorService.findAllAuthors());
		
		return "updateBook";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateBook(@Valid Book book, Errors errors, Model model)
	{
		if (errors.hasErrors())
		{
			model.addAttribute(book);
			model.addAttribute("authorsList", authorService.findAllAuthors());
			return "updateBook";
		}
		
		bookService.updateBook(book);
		
		return "redirect:/books";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteBook(@RequestParam long bookId)
	{
		bookService.deleteBookById(bookId);
		
		return "redirect:/books";
	}
	
	
	@InitBinder
    public void authorBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(Author.class, new PropertyEditorSupport() {
			
			@Override
		    public void setAsText(String text) throws IllegalArgumentException{
		        Author author = authorService.findById(Long.parseLong(text));
		        setValue(author);
		    }
			
		});
		
    }
	
	@InitBinder
	public void dateBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
	    dateFormat.setLenient(false);
		       
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
	}
	
}
