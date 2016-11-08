package com.ihordev.bookcatalog.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ihordev.bookcatalog.domain.Book;
import com.ihordev.bookcatalog.service.AuthorService;
import com.ihordev.bookcatalog.service.BookService;

public class BookControllerTest
{
	@Test
	public void shouldShowBooks() throws Exception
	{
		BookService mockBookService = mock(BookService.class);
		
		BookController controller = new BookController();
		controller.setBookService(mockBookService);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/books")).andExpect(view().name("booksList"));
		
		verify(mockBookService, atLeastOnce()).findAllBooks();
	}
	
	@Test
	public void shouldSearchBooksByNameAndShowThem() throws Exception
	{
		BookService mockBookService = mock(BookService.class);
		
		BookController controller = new BookController();
		controller.setBookService(mockBookService);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/books?bookTitle=somebook")).andExpect(view().name("booksList"));
		
		verify(mockBookService, atLeastOnce()).findBooksByTitle("somebook");
	}
	
	
	@Test
	public void shouldCreateBookAndRedirect() throws Exception
	{
		BookService mockBookService = mock(BookService.class);
		AuthorService mockAuthorService = mock(AuthorService.class);
		
		BookController controller = new BookController();
		controller.setBookService(mockBookService);
		controller.setAuthorService(mockAuthorService);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		String title = "New author forename";
		String description = "New author forename";
		String publicationDate = "1950";
		Long authorOneId = 1L;
		Long authorTwoId = 2L;
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("authors", "1");
		params.add("authors", "2");
		
		mockMvc.perform(post("/books/create")
			.param("title", title)
			.param("description", description)
			.param("publicationDate", publicationDate)
			.params(params))
			.andExpect(redirectedUrl("/books"));
		
		ArgumentCaptor<Long> authorIdCaptor = ArgumentCaptor.forClass(Long.class);
		verify(mockAuthorService, times(2)).findById(authorIdCaptor.capture());
		
		ArgumentCaptor<Book> requestParamBookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(mockBookService, atLeastOnce()).addBook(requestParamBookCaptor.capture());
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        dateFormatter.setLenient(false);
		
		assertEquals(title, requestParamBookCaptor.getValue().getTitle());
		assertEquals(description, requestParamBookCaptor.getValue().getDescription());
		assertEquals(dateFormatter.parse(publicationDate), requestParamBookCaptor.getValue().getPublicationDate());
		assertEquals(authorOneId, authorIdCaptor.getAllValues().get(0));
		assertEquals(authorTwoId, authorIdCaptor.getAllValues().get(1));
	}
	
	@Test
	public void shouldDeleteBookAndRedirect() throws Exception
	{
		BookService mockBookService = mock(BookService.class);
		
		BookController controller = new BookController();
		controller.setBookService(mockBookService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(post("/books/delete")
			.param("bookId", "1"))
			.andExpect(redirectedUrl("/books"));
		
		verify(mockBookService, atLeastOnce()).deleteBookById(1);
	}
}
