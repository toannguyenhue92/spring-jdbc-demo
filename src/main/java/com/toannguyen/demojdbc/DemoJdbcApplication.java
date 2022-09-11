package com.toannguyen.demojdbc;

import com.toannguyen.demojdbc.model.Book;
import com.toannguyen.demojdbc.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@SpringBootApplication
public class DemoJdbcApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createTable();
		List<Book> books = bookRepository.findAll();
		if (books.isEmpty()) {
			Book book = new Book("Java 11", "New future in Java 11", true);
			bookRepository.save(book);
		}
	}

	public void createTable() {
		String query = "CREATE TABLE IF NOT EXISTS book (" +
				"    id BIGSERIAL PRIMARY KEY NOT NULL," +
				"    title VARCHAR(255)," +
				"    description VARCHAR(255)," +
				"    published BOOLEAN" +
				");";
		jdbcTemplate.execute(query);
	}
}
