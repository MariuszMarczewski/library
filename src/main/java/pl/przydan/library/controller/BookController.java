package pl.przydan.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.przydan.library.model.Book;
import pl.przydan.library.model.Fine;
import pl.przydan.library.service.OrderService;

import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

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
        return orderService.borrowBook(title)
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
//        if (book.isPresent()) {
//            return ok(book.get());
//        }
//        return notFound().build();

//        return book.map(ResponseEntity :: ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/book/add", consumes = "application/json")
    public ResponseEntity<Long> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(orderService.addBook(book), CREATED);
    }

    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable Long id) {
        orderService.deleteBook(id);
        return noContent().build();
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
                .orElse(ok().build());
    }
}
