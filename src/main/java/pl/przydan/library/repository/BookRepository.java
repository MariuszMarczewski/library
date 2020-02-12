package pl.przydan.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.przydan.library.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleAndReturnDateNotNull(String title);

    List<Book> findByTitle(String title);
}
