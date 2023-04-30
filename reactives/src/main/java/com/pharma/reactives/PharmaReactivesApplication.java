package com.pharma.reactives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

@SpringBootApplication
public class PharmaReactivesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaReactivesApplication.class, args);
	}

}
