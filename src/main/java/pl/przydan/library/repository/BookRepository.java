package pl.przydan.library.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pl.przydan.library.exception.BookNotFoundException;
import pl.przydan.library.model.Book;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toSet;

@Repository
public class BookRepository {

    private static final String BOOK_NOT_FOUND = "Brak książki w repozytorium";
    private Set<Book> books = initialize();

    private Set<Book> initialize() {
        return new HashSet<>(Arrays.asList(
                new Book(41L, "Harry Potter", "JK Rowling"),
                new Book(2L, "Testy", "Kaczanowski"),
                new Book(3L, "Testy", "Kaczanowski"),
                new Book(4L, "Wladca Pierscieni", "Tolkien"),
                new Book(5L, "Wladca Pierscieni", "Tolkien"),
                new Book(6L, "Wladca Pierscieni", "Tolkien"),
                new Book(7L, "Anioly i demony", "Brown"),
                new Book(8L, "Effective Java", "Bloch"),
                new Book(9L, "Czysty kod", "Martin"),
                new Book(10L, "Czysty kod", "Martin")));
    }

    public Long addBook(Book book) {
        Long id = generateId();
        book.set_id(id);
        books.add(book);
        return id;
    }

    private Long generateId() { //Not thread safe
        OptionalLong max = books.stream()
                .mapToLong(Book::get_id)
                .max();
        return max.isPresent() ? max.getAsLong() + 1 : 1;
    }

    public boolean deleteBook(long id) {
        Book bookToRemove = books.stream()
                .filter(book -> book.getReturnDate() != null)
//                .filter(book -> Objects.nonNull(book.getReturnDate()))
                .filter(book -> book.get_id().equals(id))
                .filter(book -> book.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));
        return books.remove(bookToRemove);
    }

    public Optional<Book> borrowBook(String title, LocalDate borrowedTill) {
        Optional<Book> bookToBorrow = books.stream()
                .filter(book -> book.getTitle().equals(title))
                .filter(book -> book.getReturnDate() == null)
                .findAny();
        bookToBorrow.ifPresent(book -> book.setReturnDate(borrowedTill));
        return bookToBorrow;
    }

    public long returnBook(long id) {
        Book returnedBook = books.stream()
                .filter(book -> book.get_id().equals(id))
                .findFirst().orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));
        long overdue = 0;
        if (LocalDate.now().isAfter(returnedBook.getReturnDate())) {
            overdue = DAYS.between(LocalDate.now(), returnedBook.getReturnDate());
        }
        returnedBook.setReturnDate(null);

        return overdue;
    }


    public Set<Book> findBooks(String title) {
        return Optional.ofNullable(title)
                .map(this::filterViaTitle)
                .orElse(books);
//        return title != null ?
//                books.stream()
//                        .filter(book -> book.getTitle().equals(title))
//                        .collect(toSet()) :
//                books;
    }

    private Set<Book> filterViaTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(toSet());
    }

}
