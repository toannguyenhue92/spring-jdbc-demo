package com.toannguyen.demojdbc.repository;

import com.toannguyen.demojdbc.model.Book;

import java.util.List;

public interface BookRepository {
    int save(Book book);
    int update(Book book);
    Book findById(Long id);
    int deleteById(Long id);
    List<Book> findAll();
    List<Book> findByPublished(boolean published);
    List<Book> findByTitleContaining(String title);
}
