package lib.service;

import lib.dao.AuthorDao;
import lib.dao.BookDao;
import lib.dao.GenreDao;
import lib.model.Author;
import lib.model.Book;
import lib.model.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookService {
    private final BookDao bookDao = new BookDao();
    private final AuthorDao authorDao = new AuthorDao();
    private final GenreDao genreDao = new GenreDao();
    private final List<Book> bookList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);


    public Book create() throws SQLException {
        Book book = new Book();
        System.out.println("Please, enter book name: ");
        book.setName(scanner.next());
        System.out.println("Please, enter book isbn: ");
        book.setIsbn(scanner.next());
        System.out.println("Please, select author: ");
        List<Author> authorList = authorDao.readAll();
        System.out.println(authorList);
        System.out.println("Enter author's id or create new one by printing 0");
        String author = scanner.next();
        final int choice = Integer.parseInt(author);//try
        Optional<Author> found = authorList.stream().filter(aut -> choice == aut.getAuthor_id()).findFirst();
        if (found.isPresent()) {
            book.setAuthor(found.get());
        } else {                                          //make selection for user
            Author createdAuthor = authorDao.addManually();
            book.setAuthor(createdAuthor);
        }

        System.out.println("Please, select genre: ");
        List<Genre> genreList = genreDao.readAll();
        System.out.println(genreList);
        System.out.println("Enter genre's id or create new one by printing 0");
        String genre = scanner.next();
        final int genreSelection = Integer.parseInt(genre);
        Optional<Genre> selected = genreList.stream().filter(gen -> genreSelection == gen.getGenre_id()).findFirst();
        if (selected.isPresent()) {
            book.setGenre(selected.get());
        } else {
            Genre createdGenre = genreDao.addManually();
            book.setGenre(createdGenre);
        }
        return bookDao.create(book);

    }

    public Book read(Integer id) {
        try {
            return bookDao.readOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Book> readAll() {
        try {
            bookList.addAll(bookDao.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public boolean update(Integer id) throws SQLException {

        return bookDao.update(id);
    }

    public boolean delete(Integer id) {
        try {
            Book book = new Book();
            //  book.setId(id);
            return bookDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Book createFromXML(Book book) {

        try {
            return bookDao.createFromXML(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;

    }


}
