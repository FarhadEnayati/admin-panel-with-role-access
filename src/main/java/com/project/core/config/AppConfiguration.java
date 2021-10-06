package com.project.core.config;

import com.github.ziplet.filter.compression.CompressingFilter;
import com.project.SpringBootMyApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.Filter;
import java.util.Locale;


/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
// @EnableWebMvc
@ComponentScan(basePackageClasses = {SpringBootMyApplication.class})
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class AppConfiguration implements WebMvcConfigurer {

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor resolver
                = new LocaleChangeInterceptor();
        resolver.setParamName("lang");
        return resolver;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver
                = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("fa"));
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public Filter compressionFilter() {
        return new CompressingFilter();
    }

}
