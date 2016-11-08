package com.ihordev.bookcatalog.web;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.service.AuthorService;


@Controller
@RequestMapping("/authors")
public class AuthorController {

	private AuthorService authorService;
	
    @Autowired
    public void setAuthorService(AuthorService authorService)
	{
		this.authorService = authorService;
	}
	
    
	@RequestMapping(method = RequestMethod.GET)
	public String authors(Model model)
	{
		model.addAttribute("authors", authorService.findAllAuthors());
		
		return "authorsList";
	}
	
	@RequestMapping(value = "/{authorId}" , method = RequestMethod.GET)
	public String showAuthorsBooks(@PathVariable long authorId, Model model)
	{
		model.addAttribute("author", authorService.findByIdWithBooks(authorId));
		
		return "authorsBooksList";
	}

	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showAuthorCreationForm(Model model)
	{	
		model.addAttribute("author", new Author());
		return "createAuthor";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createAuthor(@Valid Author author, Errors errors, Model model)
	{
		if (errors.hasErrors())
		{
			model.addAttribute("author", author);
			return "createAuthor";
		}
		
		authorService.addAuthor(author);
		return "redirect:/authors";
	}
	
	@RequestMapping(value = "/{authorId}/update", method = RequestMethod.GET)
	public String showAuthorUpdateForm(@PathVariable long authorId, Model model)
	{	
		model.addAttribute("author", authorService.findById(authorId));
		
		return "updateAuthor";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateAuthor(@Valid Author author, Errors errors, Model model)
	{
		if (errors.hasErrors())
		{
			model.addAttribute("author", author);
			return "updateAuthor";
		}
		
		authorService.updateAuthor(author);
		
		return "redirect:/authors";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteAuthor(@RequestParam long authorId)
	{
		authorService.deleteAuthorById(authorId);
		
		return "redirect:/authors";
	}
}
