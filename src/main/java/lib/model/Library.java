package lib.model;

import lib.service.AuthorService;
import lib.service.BookService;
import lib.service.GenreService;
import lib.util.DOMParser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Library {

    BookService bookService = new BookService();
    AuthorService authorService = new AuthorService();
    GenreService genreService = new GenreService();
    DOMParser domParser = new DOMParser();

    List<Book> books = new ArrayList<>(bookService.readAll());
    List<Author> authors = new ArrayList<>(authorService.readAll());
    List<Genre> genres = new ArrayList<>(genreService.readAll());


    private void convertXmlToBook() throws SQLException {
        books = domParser.createBook();

        for (Book book : books) {
            book.getId();
            book.getName();
            book.getAuthor().getAuthor_id();
            book.getGenre().getGenre_id();
            book.getIsbn();
            bookService.createFromXML(book);

        }
    }

    private void convertXmlToAuthor() throws SQLException {
        authors = domParser.createAuthor();

        for (Author author : authors) {
            author.getAuthor_id();
            author.getAuthor_name();
            authorService.create(author);
        }

    }

    private void convertXmlToGenre() throws SQLException {
        genres = domParser.createGenre();

        for (Genre genre : genres) {
            genre.getGenre_id();
            genre.getGenre_name();
            genreService.create(genre);
        }
    }

    public void addFromFile() {
        try {
            convertXmlToAuthor();
            convertXmlToGenre();
            convertXmlToBook();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void loadAll() {
        bookService.readAll();
    }

    public void loadBook(Integer userChoice) {
        bookService.read(userChoice);
    }

    public void addBookManually() {
        try {
            bookService.create();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Integer id) {
        bookService.delete(id);
    }

    public void edit(Integer id) {
        try {
            bookService.update(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}