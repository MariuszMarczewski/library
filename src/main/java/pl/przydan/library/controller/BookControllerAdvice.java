package pl.przydan.library.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.przydan.library.exception.BookNotFoundException;

@ControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler({BookNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<String> handle(BookNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
