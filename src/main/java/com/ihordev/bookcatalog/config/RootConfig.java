package com.ihordev.bookcatalog.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ihordev.bookcatalog.util.SiteNavigation;

@Configuration
@Import(DataConfig.class)
public class RootConfig
{
    @Bean
    public MessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/properties/i18n/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public SiteNavigation siteNavigation(MessageSource messageSource)
    {
        SiteNavigation siteNavigation;

        DocumentBuilder dBuilder;

        try
        {
            InputStream in = new ClassPathResource("/site-navigation.xml").getInputStream();

            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(in);

            doc.getDocumentElement().normalize();

            siteNavigation = SiteNavigation.buildFrom(doc, messageSource);

        } catch (IOException | ParserConfigurationException | SAXException e)
        {
            siteNavigation = SiteNavigation.buildEmpty();
        }

        return siteNavigation;
    }

}
