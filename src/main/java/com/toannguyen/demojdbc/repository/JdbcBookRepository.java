package com.toannguyen.demojdbc.repository;

import com.toannguyen.demojdbc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int save(Book book) {
        String query = "INSERT INTO book (title, description, published) VALUES (:title, :description, :published)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("description", book.getDescription())
                .addValue("published", book.isPublished());
        return jdbcTemplate.update(query, parameters);
    }

    @Override
    public int update(Book book) {
        String query = "UPDATE book SET title=:title, description=:description, published=:published WHERE id=:id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("description", book.getDescription())
                .addValue("published", book.isPublished())
                .addValue("id", book.getId());
        return jdbcTemplate.update(query, parameters);
    }

    @Override
    public Book findById(Long id) {
        String query = "SELECT * FROM book WHERE id=:id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(query, parameters, BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM book WHERE id=:id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.update(query, parameters);
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public List<Book> findByPublished(boolean published) {
        String query = "SELECT * FROM book WHERE published=:published";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("published", published);
        return jdbcTemplate.query(query, parameters, BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public List<Book> findByTitleContaining(String title) {
        String query = "SELECT * FROM book WHERE title LIKE :title";
        String containTitle = "%" + title + "%";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", containTitle);
        return jdbcTemplate.query(query, parameters, BeanPropertyRowMapper.newInstance(Book.class));
    }
}
