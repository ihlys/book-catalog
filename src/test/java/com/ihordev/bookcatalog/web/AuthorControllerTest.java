package com.ihordev.bookcatalog.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ihordev.bookcatalog.config.TestRootConfig;
import com.ihordev.bookcatalog.config.TestWebConfig;
import com.ihordev.bookcatalog.domain.Author;
import com.ihordev.bookcatalog.service.AuthorService;
import com.ihordev.bookcatalog.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRootConfig.class, TestWebConfig.class})
@WebAppConfiguration
@ActiveProfiles("web-test")
public class AuthorControllerTest
{

    private MockMvc mockMvc;

    @Autowired
    private AuthorService mockAuthorService;

    @Autowired
    private BookService mockBookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp()
    {
        Mockito.reset(mockAuthorService);
        Mockito.reset(mockBookService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldShowAuthors() throws Exception
    {
        mockMvc.perform(get("/authors")).andExpect(view().name("authorsList"));
    }

    @Test
    public void shouldShowBooksForAuthor() throws Exception
    {
        Author author = new Author();
        author.setId(2L);
        
        when(mockAuthorService.findByIdWithBooks(eq(2L))).thenReturn(author);

        mockMvc.perform(get("/authors/2"))
            .andExpect(view()
            .name("authorsBooksList"))
            .andExpect(model().attributeExists("author"));
    }

    @Test
    public void shouldCreateAuthorAndRedirect() throws Exception
    {
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
        mockMvc.perform(post("/authors/delete")
                .param("authorId", "1"))
                .andExpect(redirectedUrl("/authors"));

        verify(mockAuthorService, atLeastOnce()).deleteAuthorById(1);
    }

}
