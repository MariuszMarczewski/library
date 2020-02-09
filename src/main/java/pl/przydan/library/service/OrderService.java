package pl.przydan.library.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.przydan.library.model.Book;
import pl.przydan.library.repository.BookRepository;
import pl.przydan.library.model.Fine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    private final BookRepository bookRepository;

    public OrderService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Optional<Book> borrowBook(String title) {
        return bookRepository.borrowBook(title, LocalDate.now().plusDays(30));
    }

    public Optional<Fine> returnBook(long id) {
        long overdue= bookRepository.returnBook(id);
        return overdue >= 1 ? Optional.of(new Fine(BigDecimal.ONE, overdue)) : Optional.empty();
    }

    public Long addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public boolean deleteBook(long id) {
        return bookRepository.deleteBook(id);
    }

    public Set<Book> findBooks(String title) {
        return bookRepository.findBooks(title);
    }
}
