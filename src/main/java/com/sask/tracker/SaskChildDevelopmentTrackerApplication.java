package com.sask.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.sask.tracker.config.AwsProperties;

@SpringBootApplication
@EnableConfigurationProperties({AwsProperties.class})
public class SaskChildDevelopmentTrackerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SaskChildDevelopmentTrackerApplication.class, args);
	}
	
	 // This method is used when deploying to an external servlet container (e.g., Tomcat)
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SaskChildDevelopmentTrackerApplication.class);
    }

}
