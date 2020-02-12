package pl.przydan.library.service;

import org.springframework.stereotype.Service;
import pl.przydan.library.exception.BookNotFoundException;
import pl.przydan.library.model.Book;
import pl.przydan.library.model.Fine;
import pl.przydan.library.repository.BookRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class OrderService {
    private final BookRepository bookRepository;

    public OrderService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Optional<Book> borrowBook(String title) {
        Optional<Book> any = bookRepository.findByTitleAndReturnDateNotNull(title).stream()
                .findAny();
        any.ifPresentOrElse(book -> book.setReturnDate(LocalDate.now().plusDays(30)),
                () -> {
                    throw new BookNotFoundException("Ni ma");
                });
        bookRepository.save(any.get());
        return any;
    }

    public Long addBook(Book book) {
        return bookRepository.save(book).get_id();
    }

    public Optional<Fine> returnBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent() && book.get().getReturnDate() != null) {
            long overdue = 0;
            Book returnedBook = book.get();
            if (LocalDate.now().isAfter(returnedBook.getReturnDate())) {
                overdue = DAYS.between(LocalDate.now(), returnedBook.getReturnDate());
            }
            returnedBook.setReturnDate(null);
            bookRepository.save(returnedBook);
            return overdue >= 1 ? Optional.of(new Fine(BigDecimal.ONE, overdue)) : Optional.empty();
        }
        throw new BookNotFoundException("Ni ma ");
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public Set<Book> findBooks(String title) {
        if (title != null) {
            return new HashSet<>(bookRepository.findByTitle(title));
        }
        return new HashSet<>(bookRepository.findAll());
//        return Optional.ofNullable(title)
//                .map(bookRepository::findByTitle)
//                .orElse(new HashSet<>(bookRepository.findAll()));
    }
}
