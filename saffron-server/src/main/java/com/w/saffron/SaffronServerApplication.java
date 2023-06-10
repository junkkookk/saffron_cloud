package com.w.saffron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author w
 */
@SpringBootApplication
public class SaffronServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SaffronServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SaffronServerApplication.class);
	}

}
