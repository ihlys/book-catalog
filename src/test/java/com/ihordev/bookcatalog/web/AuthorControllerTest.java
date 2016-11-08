package com.ihordev.bookcatalog.web;



import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.service.AuthorService;

public class AuthorControllerTest 
{

	@Test
	public void shouldShowAuthors() throws Exception
	{
		AuthorService mockAuthorService = mock(AuthorService.class);
		
		AuthorController controller = new AuthorController();
		controller.setAuthorService(mockAuthorService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/authors")).andExpect(view().name("authorsList"));
	}
	
	@Test
	public void shouldShowBooksForAuthor() throws Exception
	{
		AuthorService mockAuthorService = mock(AuthorService.class);
		when(mockAuthorService.findByIdWithBooks(anyLong())).thenReturn(new Author());
		
		AuthorController controller = new AuthorController();
		controller.setAuthorService(mockAuthorService);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/authors/2"))
			.andExpect(view().name("authorsBooksList"))
			.andExpect(model().attributeExists("author"));
	}
	
	@Test
	public void shouldCreateAuthorAndRedirect() throws Exception
	{
		AuthorService mockAuthorService = mock(AuthorService.class);
		
		AuthorController controller = new AuthorController();
		controller.setAuthorService(mockAuthorService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		String forname = "New author forename";
		String surname = "New author surname";

		mockMvc.perform(post("/authors/create")
			.param("forename", forname)
			.param("surname", surname))
			.andExpect(redirectedUrl("/authors"));
		
		ArgumentCaptor<Author> argument = ArgumentCaptor.forClass(Author.class);
		
		verify(mockAuthorService, atLeastOnce()).addAuthor(argument.capture());
		
		assertEquals(forname, argument.getValue().getForename());
		assertEquals(surname, argument.getValue().getSurname());
	}
	
	@Test
	public void shouldUpdateAuthorAndRedirect() throws Exception
	{
		AuthorService mockAuthorService = mock(AuthorService.class);
		
		AuthorController controller = new AuthorController();
		controller.setAuthorService(mockAuthorService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		Author author = new Author();
		author.setForename("Author new forename");
		author.setSurname("Author new surname");
		
		mockMvc.perform(post("/authors/update")
			.param("forename", author.getForename())
			.param("surname", author.getSurname()))
			.andExpect(redirectedUrl("/authors"));
		
		ArgumentCaptor<Author> argument = ArgumentCaptor.forClass(Author.class);
		
		verify(mockAuthorService, atLeastOnce()).updateAuthor(argument.capture());
		
		assertEquals(author.getForename(), argument.getValue().getForename());
		assertEquals(author.getSurname(), argument.getValue().getSurname());
	}
	
	@Test
	public void shouldDeleteAuthorAndRedirect() throws Exception
	{
		AuthorService mockAuthorService = mock(AuthorService.class);
		
		AuthorController controller = new AuthorController();
		controller.setAuthorService(mockAuthorService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		
		mockMvc.perform(post("/authors/delete")
			.param("authorId", "1"))
			.andExpect(redirectedUrl("/authors"));
		
		verify(mockAuthorService, atLeastOnce()).deleteAuthorById(1);
	}
	
}
