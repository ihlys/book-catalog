package com.ihordev.bookcatalog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.ihordev.bookcatalog.service.AuthorService;
import com.ihordev.bookcatalog.service.BookService;

@Component
@Import(WebConfig.class)
public class TestWebConfig
{
    @Bean
    public AuthorService mockAuthorService()
    {
        return Mockito.mock(AuthorService.class);
    }
    
    @Bean
    public BookService mockBookService()
    {
        return Mockito.mock(BookService.class);
    }
}
