package lib.model;

public class Book {
    private Integer id;
    private String name;
    private Author author;
    private Genre genre;
    private String isbn;

    public Book(Integer id, String name, Author author, Genre genre, String isbn) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public Book() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
