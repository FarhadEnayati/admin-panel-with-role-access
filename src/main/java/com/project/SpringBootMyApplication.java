package com.project;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@SpringBootApplication
@EnableCaching
public class SpringBootMyApplication extends SpringBootServletInitializer {

    @Autowired
    private ResourceLoader resourceLoader;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMyApplication.class, args);
        System.out.println("\n********************** Application Started **********************\n");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootMyApplication.class);
    }

    @Bean
    public DozerBeanMapperFactoryBean dozer() {
        DozerBeanMapperFactoryBean dozer = new DozerBeanMapperFactoryBean();
        dozer.setMappingFiles(loadResources());
        return dozer;
    }

    private Resource[] loadResources() {
        Resource[] resources = null;
        try {
            resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources("classpath*:dozer/**/*.dzr.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}