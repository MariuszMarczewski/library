package pl.przydan.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.przydan.library.model.Book;
import pl.przydan.library.model.Fine;
import pl.przydan.library.service.OrderService;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
public class BookController {

    private final OrderService orderService;

    public BookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/books", produces = "application/json")
    public Set<Book> getBooks(@RequestParam(required = false) String title) {
        return orderService.findBooks(title);
    }

    @GetMapping(value = "/book/order/{title}", produces = "application/json")
    public ResponseEntity<Book> borrowBook(@PathVariable String title) {
        Optional<Book> book = orderService.borrowBook(title);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.notFound().build();

//        return orderService.borrowBook(title)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());

//        return book.map(ResponseEntity :: ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/book/add", consumes = "application/json")
    public ResponseEntity<Long> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(orderService.addBook(book), CREATED);
    }

    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity<?> removeBook(@PathVariable Long id) {
        boolean deleteBook = orderService.deleteBook(id);
        if (deleteBook) {
            return noContent().build();
        }
        return badRequest().body("Could not delete a book with id" + id);
    }

    @GetMapping(value = "book/return/{id}", produces = "application/json")
    public ResponseEntity<Fine> returnBook(@PathVariable Long id) {
//        Optional<Fine> fine = orderService.returnBook(id);
//
//        if (fine.isPresent()) {
//            return ResponseEntity.ok(fine.get());
//        }
//        return ResponseEntity.ok().build();
        return orderService.returnBook(id)
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.ok().build());
    }
}
