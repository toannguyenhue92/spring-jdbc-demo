package com.toannguyen.demojdbc.controller;

import com.toannguyen.demojdbc.model.Book;
import com.toannguyen.demojdbc.repository.BookRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBook(@RequestParam(required = false) String title) {
        try {
            List<Book> books = new ArrayList<>();
            if (title == null) {
                books.addAll(bookRepository.findAll());
            } else {
                books.addAll(bookRepository.findByTitleContaining(title));
            }
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        try {
            Book book = bookRepository.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (EmptyResultDataAccessException emptyResultException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try {
            int rowEffected = bookRepository.save(book);
            if (rowEffected == 1) {
                return new ResponseEntity<>("Book was created successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ExceptionUtils.getStackTrace(ex), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable long id, @RequestBody Book book) {
        try {
            Book updateBook = bookRepository.findById(id);
            updateBook.setTitle(book.getTitle());
            updateBook.setDescription(book.getDescription());
            updateBook.setPublished(book.isPublished());
            bookRepository.update(updateBook);
            return new ResponseEntity<>("Book was updated successfully.", HttpStatus.OK);
        } catch (EmptyResultDataAccessException emptyResultException) {
            return new ResponseEntity<>("Cannot found this book.", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        try {
            int result = bookRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot found this book.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Book was deleted successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/published")
    public ResponseEntity<List<Book>> getBookPublished() {
        try {
            List<Book> books = bookRepository.findByPublished(true);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
