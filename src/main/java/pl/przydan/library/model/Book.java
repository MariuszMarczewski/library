package pl.przydan.library.model;


import java.time.LocalDate;
import java.util.Objects;


public class Book {
    private Long _id;
    private String author;
    private String title;
    private LocalDate returnDate;

    public Book() {
    }

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public Book(Long _id, String title, String author) {
        this._id = _id;
        this.author = author;
        this.title = title;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "_id=" + _id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(_id, book._id) &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                Objects.equals(returnDate, book.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, author, title, returnDate);
    }
}
